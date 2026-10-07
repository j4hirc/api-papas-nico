package com.api.nico.api_papas_nico.compra_comida.service.implementation;

import com.api.nico.api_papas_nico.comidas.repository.ComidaRepository;
import com.api.nico.api_papas_nico.common.Fechas;
import com.api.nico.api_papas_nico.compra_comida.dto.request.CompraComidaRequestDTO;
import com.api.nico.api_papas_nico.compra_comida.dto.response.CompraComidaResponseDTO;
import com.api.nico.api_papas_nico.compra_comida.model.*;
import com.api.nico.api_papas_nico.compra_comida.repository.*;
import com.api.nico.api_papas_nico.compra_comida.service.CompraComidaService;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.server.ResponseStatusException;

@Service
@Validated
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CompraComidaServiceImpl implements CompraComidaService {

    private final CabeceraCompraRepository cabeceras;
    private final DetalleCompraRepository detalles;
    private final ComidaRepository productos;
    private final CompraComidaMapper mapper;

    @Override
    @Transactional
    public CompraComidaResponseDTO createCompra(CompraComidaRequestDTO dto) {
        return guardar(new CabeceraCompraComida(), dto, List.of());
    }

    @Override
    @Transactional
    public CompraComidaResponseDTO updateCompra(
            Long id,
            CompraComidaRequestDTO dto
    ) {
        var c = cabeceras.bloquear(id).orElseThrow(() -> noExiste(id));
        var viejos = detalles.findByCabeceraCompra_Id(id);

        if (c.getFecha() == null
                || viejos.stream().anyMatch(d -> d.getPrecioUnitario() == null)) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Histórico incompleto: conciliar fecha/importes antes de editar"
            );
        }

        return guardar(c, dto, viejos);
    }

    private CompraComidaResponseDTO guardar(
            CabeceraCompraComida c,
            CompraComidaRequestDTO dto,
            List<DetalleCompraComida> viejos
    ) {
        if (c.getId() == null || dto.getFechaCompra() != null) {
            var f = Fechas.resolver(dto.getFechaCompra());
            c.setFecha(f.toLocalDate());
            c.setHora(f.toLocalTime());
            c.setFechaCompra(f.toOffsetDateTime().toString());
        }

        Map<Long, BigDecimal> anteriores = new HashMap<>();
        for (var d : viejos) {
            anteriores.put(d.getComidaId().getId(), d.getPrecioUnitario());
        }

        List<DetalleCompraComida> nuevos = new ArrayList<>();
        Set<Long> ids = new HashSet<>();
        BigDecimal total = BigDecimal.ZERO;

        for (var linea : dto.getDetalles()) {
            if (!ids.add(linea.getComidaId())) {
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "Producto repetido; agrupa su cantidad"
                );
            }

            var producto = productos.findById(linea.getComidaId())
                    .orElseThrow(() -> new ResponseStatusException(
                            HttpStatus.NOT_FOUND,
                            "Producto inexistente: " + linea.getComidaId()
                    ));

            BigDecimal precio = anteriores.getOrDefault(
                    producto.getId(),
                    producto.getPrecio()
            );

            if (precio == null || precio.signum() <= 0) {
                throw new ResponseStatusException(
                        HttpStatus.CONFLICT,
                        "Producto sin precio válido"
                );
            }

            var d = new DetalleCompraComida();
            d.setCabeceraCompra(c);
            d.setComidaId(producto);
            d.setCantidad(linea.getCantidad());
            d.setPrecioUnitario(precio);

            total = total.add(
                    precio.multiply(BigDecimal.valueOf(d.getCantidad()))
            );

            nuevos.add(d);
        }

        c.setTotal(total);
        cabeceras.save(c);

        detalles.deleteAll(viejos);
        detalles.flush();
        detalles.saveAll(nuevos);

        return mapper.toResponseDTO(c, nuevos);
    }

    @Override
    public CompraComidaResponseDTO getCompraById(Long id) {
        var c = cabeceras.findById(id).orElseThrow(() -> noExiste(id));

        return mapper.toResponseDTO(
                c,
                detalles.findByCabeceraCompra_Id(id)
        );
    }

    @Override
    public List<CompraComidaResponseDTO> getAllCompras(
            LocalDate desde,
            LocalDate hasta
    ) {
        Fechas.rango(desde, hasta);

        if ((desde == null) != (hasta == null)) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Envía desde y hasta juntos"
            );
        }

        var lista = desde == null
                ? cabeceras.findAll()
                : cabeceras.findByFechaBetweenOrderByFechaAscHoraAscIdAsc(
                desde,
                hasta
        );

        return lista.stream()
                .map(c -> mapper.toResponseDTO(
                        c,
                        detalles.findByCabeceraCompra_Id(c.getId())
                ))
                .toList();
    }

    @Override
    @Transactional
    public void deleteCompra(Long id) {
        var c = cabeceras.bloquear(id).orElseThrow(() -> noExiste(id));

        detalles.deleteAll(detalles.findByCabeceraCompra_Id(id));
        detalles.flush();
        cabeceras.delete(c);
    }

    private ResponseStatusException noExiste(Long id) {
        return new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Operación inexistente: " + id
        );
    }
}
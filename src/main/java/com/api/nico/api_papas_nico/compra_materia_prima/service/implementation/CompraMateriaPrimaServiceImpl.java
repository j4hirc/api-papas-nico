package com.api.nico.api_papas_nico.compra_materia_prima.service.implementation;

import com.api.nico.api_papas_nico.common.Fechas;
import com.api.nico.api_papas_nico.compra_materia_prima.dto.request.CompraMateriaPrimaRequestDTO;
import com.api.nico.api_papas_nico.compra_materia_prima.dto.response.CompraMateriaPrimaResponseDTO;
import com.api.nico.api_papas_nico.compra_materia_prima.model.*;
import com.api.nico.api_papas_nico.compra_materia_prima.repository.*;
import com.api.nico.api_papas_nico.compra_materia_prima.service.CompraMateriaPrimaService;
import com.api.nico.api_papas_nico.materia_prima.repository.MateriaPrimaRepository;
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
public class CompraMateriaPrimaServiceImpl
        implements CompraMateriaPrimaService {

    private final CabeceraCompraMPRepository cabeceras;
    private final DetalleCompraMPRepository detalles;
    private final MateriaPrimaRepository productos;
    private final CompraMateriaPrimaMapper mapper;

    @Override
    @Transactional
    public CompraMateriaPrimaResponseDTO createCompra(
            CompraMateriaPrimaRequestDTO dto
    ) {
        return guardar(new CabeceraCompraMateriaPrima(), dto, List.of());
    }

    @Override
    @Transactional
    public CompraMateriaPrimaResponseDTO updateCompra(
            Long id,
            CompraMateriaPrimaRequestDTO dto
    ) {
        var c = cabeceras.bloquear(id).orElseThrow(() -> noExiste(id));
        var viejos = detalles.findByCabeceraCompra_Id(id);

        if (c.getFecha() == null
                || viejos.stream().anyMatch(d -> d.getImportePagado() == null)) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Histórico incompleto: conciliar fecha/importes antes de editar"
            );
        }

        return guardar(c, dto, viejos);
    }

    private CompraMateriaPrimaResponseDTO guardar(
            CabeceraCompraMateriaPrima c,
            CompraMateriaPrimaRequestDTO dto,
            List<DetalleCompraMateriaPrima> viejos
    ) {
        if (c.getId() == null || dto.getFechaCompra() != null) {
            var f = Fechas.resolver(dto.getFechaCompra());
            c.setFecha(f.toLocalDate());
            c.setHora(f.toLocalTime());
            c.setFechaCompra(f.toOffsetDateTime().toString());
        }

        List<DetalleCompraMateriaPrima> nuevos = new ArrayList<>();
        BigDecimal total = BigDecimal.ZERO;

        for (var linea : dto.getDetalles()) {
            var producto = productos.findById(linea.getMateriaPrimaId())
                    .orElseThrow(() -> new ResponseStatusException(
                            HttpStatus.NOT_FOUND,
                            "Producto inexistente: " + linea.getMateriaPrimaId()
                    ));

            var d = new DetalleCompraMateriaPrima();
            d.setCabeceraCompra(c);
            d.setMateriaPrima(producto);
            d.setCantidad(linea.getCantidad());
            d.setUnidad(linea.getUnidad().trim());
            d.setImportePagado(linea.getImportePagado());

            total = total.add(d.getImportePagado());
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
    public CompraMateriaPrimaResponseDTO getCompraById(Long id) {
        var c = cabeceras.findById(id).orElseThrow(() -> noExiste(id));

        return mapper.toResponseDTO(
                c,
                detalles.findByCabeceraCompra_Id(id)
        );
    }

    @Override
    public List<CompraMateriaPrimaResponseDTO> getAllCompras(
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
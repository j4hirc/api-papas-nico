package com.api.nico.api_papas_nico.compra_comida.service.implementation;

import com.api.nico.api_papas_nico.comidas.model.Comidas;
import com.api.nico.api_papas_nico.comidas.repository.ComidaRepository;
import com.api.nico.api_papas_nico.compra_comida.dto.request.CompraComidaRequestDTO;
import com.api.nico.api_papas_nico.compra_comida.dto.response.CompraComidaResponseDTO;
import com.api.nico.api_papas_nico.compra_comida.model.CabeceraCompraComida;
import com.api.nico.api_papas_nico.compra_comida.model.DetalleCompraComida;
import com.api.nico.api_papas_nico.compra_comida.repository.CabeceraCompraRepository;
import com.api.nico.api_papas_nico.compra_comida.repository.DetalleCompraRepository;
import com.api.nico.api_papas_nico.compra_comida.service.CompraComidaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CompraComidaServiceImpl implements CompraComidaService {

    private final CabeceraCompraRepository cabeceraRepository;
    private final DetalleCompraRepository detalleRepository;
    private final ComidaRepository comidaRepository;
    private final CompraComidaMapper compraMapper;

    @Override
    @Transactional
    public CompraComidaResponseDTO createCompra(CompraComidaRequestDTO requestDTO) {

        CabeceraCompraComida cabecera = new CabeceraCompraComida();
        cabecera.setFechaCompra(requestDTO.getFechaCompra());
        cabecera.setTotal(0.0);

        CabeceraCompraComida savedCabecera = cabeceraRepository.save(cabecera);

        List<DetalleCompraComida> detallesGuardados = new ArrayList<>();

        if (requestDTO.getDetalles() != null && !requestDTO.getDetalles().isEmpty()) {

            List<DetalleCompraComida> detallesToSave = requestDTO.getDetalles().stream().map(detDTO -> {
                Comidas comida = comidaRepository.findById(detDTO.getComidaId())
                        .orElseThrow(() -> new RuntimeException("Comida no encontrada con ID: " + detDTO.getComidaId()));

                DetalleCompraComida detalle = new DetalleCompraComida();
                detalle.setCabeceraCompra(savedCabecera);
                detalle.setComidaId(comida);
                detalle.setCantidad(detDTO.getCantidad());

                return detalle;
            }).toList();

            detallesGuardados = detalleRepository.saveAll(detallesToSave);

            // 3. Calcular el total acumulado usando Streams sobre los registros ya guardados
            double totalCalculado = detallesGuardados.stream()
                    .mapToDouble(detalle -> detalle.getComidaId().getPrecio() * detalle.getCantidad())
                    .sum();

            savedCabecera.setTotal(totalCalculado);
        }

        cabeceraRepository.save(savedCabecera);

        return compraMapper.toResponseDTO(savedCabecera, detallesGuardados);
    }

    @Override
    public CompraComidaResponseDTO getCompraById(Long id) {
        CabeceraCompraComida cabecera = cabeceraRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Compra no encontrada con id: " + id));

        List<DetalleCompraComida> detalles = detalleRepository.findByCabeceraCompra_Id(id);

        return compraMapper.toResponseDTO(cabecera, detalles);
    }

    @Override
    public List<CompraComidaResponseDTO> getAllCompras() {
        List<CabeceraCompraComida> cabeceras = cabeceraRepository.findAll();

        return cabeceras.stream().map(cabecera -> {
            List<DetalleCompraComida> detalles = detalleRepository.findByCabeceraCompra_Id(cabecera.getId());
            return compraMapper.toResponseDTO(cabecera, detalles);
        }).toList();
    }

    @Override
    @Transactional
    public void deleteCompra(Long id) {
        CabeceraCompraComida cabecera = cabeceraRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Compra no encontrada con id: " + id));

        List<DetalleCompraComida> detalles = detalleRepository.findByCabeceraCompra_Id(id);
        detalleRepository.deleteAll(detalles);

        cabeceraRepository.delete(cabecera);
    }
}
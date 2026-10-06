package com.api.nico.api_papas_nico.compra_materia_prima.service.implementation;

import com.api.nico.api_papas_nico.compra_materia_prima.dto.CompraMateriaPrimaRequestDTO;
import com.api.nico.api_papas_nico.compra_materia_prima.dto.CompraMateriaPrimaResponseDTO;
import com.api.nico.api_papas_nico.compra_materia_prima.dto.DetalleCompraMPRequestDTO;
import com.api.nico.api_papas_nico.compra_materia_prima.model.CabeceraCompraMateriaPrima;
import com.api.nico.api_papas_nico.compra_materia_prima.model.DetalleCompraMateriaPrima;
import com.api.nico.api_papas_nico.compra_materia_prima.repository.CabeceraCompraMPRepository;
import com.api.nico.api_papas_nico.compra_materia_prima.repository.DetalleCompraMPRepository;
import com.api.nico.api_papas_nico.compra_materia_prima.service.CompraMateriaPrimaService;
import com.api.nico.api_papas_nico.materia_prima.model.MateriaPrima;
import com.api.nico.api_papas_nico.materia_prima.repository.MateriaPrimaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CompraMateriaPrimaServiceImpl implements CompraMateriaPrimaService {

    private final CabeceraCompraMPRepository cabeceraRepository;
    private final DetalleCompraMPRepository detalleRepository;
    private final MateriaPrimaRepository materiaPrimaRepository;
    private final CompraMateriaPrimaMapper mapper;
    @Override
    @Transactional
    public CompraMateriaPrimaResponseDTO createCompra(CompraMateriaPrimaRequestDTO requestDTO) {

        CabeceraCompraMateriaPrima cabecera = new CabeceraCompraMateriaPrima();
        cabecera.setFechaCompra(requestDTO.getFechaCompra());
        cabecera.setTotal(0.0);
        CabeceraCompraMateriaPrima savedCabecera = cabeceraRepository.save(cabecera);

        List<DetalleCompraMateriaPrima> detallesGuardados = new ArrayList<>();

        if (requestDTO.getDetalles() != null && !requestDTO.getDetalles().isEmpty()) {

            List<DetalleCompraMateriaPrima> detallesToSave = requestDTO.getDetalles().stream().map(detDTO -> {
                MateriaPrima materiaPrima = materiaPrimaRepository.findById(detDTO.getMateriaPrimaId())
                        .orElseThrow(() -> new RuntimeException("Materia Prima no encontrada con id: " + detDTO.getMateriaPrimaId()));

                DetalleCompraMateriaPrima detalle = new DetalleCompraMateriaPrima();
                detalle.setCabeceraCompra(savedCabecera);
                detalle.setMateriaPrima(materiaPrima);
                detalle.setCantidad(detDTO.getCantidad());

                return detalle;
            }).toList();

            detallesGuardados = detalleRepository.saveAll(detallesToSave);

            double totalCalculado = detallesGuardados.stream()
                    .mapToDouble(detalle -> detalle.getMateriaPrima().getPrecio() * detalle.getCantidad())
                    .sum();

            savedCabecera.setTotal(totalCalculado);
        }

        cabeceraRepository.save(savedCabecera);

        return mapper.toResponseDTO(savedCabecera, detallesGuardados);
    }

    @Override
    public CompraMateriaPrimaResponseDTO getCompraById(Long cabeceraId) {
        CabeceraCompraMateriaPrima cabecera = cabeceraRepository.findById(cabeceraId)
                .orElseThrow(() -> new RuntimeException("Compra no encontrada con id: " + cabeceraId));

        List<DetalleCompraMateriaPrima> detalles = detalleRepository.findByCabeceraCompra_Id(cabeceraId);
        return mapper.toResponseDTO(cabecera, detalles);
    }

    @Override
    public List<CompraMateriaPrimaResponseDTO> getAllCompras() {
        List<CabeceraCompraMateriaPrima> cabeceras = cabeceraRepository.findAll();

        return cabeceras.stream().map(cabecera -> {
            List<DetalleCompraMateriaPrima> detalles = detalleRepository.findByCabeceraCompra_Id(cabecera.getId());
            return mapper.toResponseDTO(cabecera, detalles);
        }).toList();
    }

    @Override
    @Transactional
    public void deleteCompra(Long cabeceraId) {
        CabeceraCompraMateriaPrima cabecera = cabeceraRepository.findById(cabeceraId)
                .orElseThrow(() -> new RuntimeException("Compra no encontrada con id: " + cabeceraId));

        List<DetalleCompraMateriaPrima> detalles = detalleRepository.findByCabeceraCompra_Id(cabeceraId);
        detalleRepository.deleteAll(detalles);
        cabeceraRepository.delete(cabecera);
    }
}
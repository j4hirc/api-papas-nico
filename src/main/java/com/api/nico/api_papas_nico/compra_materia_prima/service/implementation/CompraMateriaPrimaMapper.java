package com.api.nico.api_papas_nico.compra_materia_prima.service.implementation;

import com.api.nico.api_papas_nico.compra_materia_prima.dto.CompraMateriaPrimaResponseDTO;
import com.api.nico.api_papas_nico.compra_materia_prima.dto.DetalleCompraMPResponseDTO;
import com.api.nico.api_papas_nico.compra_materia_prima.model.CabeceraCompraMateriaPrima;
import com.api.nico.api_papas_nico.compra_materia_prima.model.DetalleCompraMateriaPrima;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class CompraMateriaPrimaMapper {

    public CompraMateriaPrimaResponseDTO toResponseDTO(CabeceraCompraMateriaPrima cabecera, List<DetalleCompraMateriaPrima> detalles) {
        CompraMateriaPrimaResponseDTO dto = new CompraMateriaPrimaResponseDTO();
        dto.setId(cabecera.getId());
        dto.setFechaCompra(cabecera.getFechaCompra());
        dto.setTotal(cabecera.getTotal());

        if (detalles != null && !detalles.isEmpty()) {
            List<DetalleCompraMPResponseDTO> detallesDTO = detalles.stream().map(detalle -> {
                DetalleCompraMPResponseDTO detDTO = new DetalleCompraMPResponseDTO();
                detDTO.setId(detalle.getId());
                detDTO.setMateriaPrimaId(detalle.getMateriaPrima().getId());
                detDTO.setNombreMateriaPrima(detalle.getMateriaPrima().getNombre());
                detDTO.setCantidad(detalle.getCantidad());
                detDTO.setSubtotal(detalle.getMateriaPrima().getPrecio() * detalle.getCantidad());
                return detDTO;
            }).collect(Collectors.toList());

            dto.setDetalles(detallesDTO);
        } else {
            dto.setDetalles(new ArrayList<>());
        }

        return dto;
    }
}

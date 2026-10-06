package com.api.nico.api_papas_nico.compra_comida.service.implementation;

import com.api.nico.api_papas_nico.compra_comida.dto.response.CompraComidaResponseDTO;
import com.api.nico.api_papas_nico.compra_comida.dto.response.DetalleCompraResponseDTO;
import com.api.nico.api_papas_nico.compra_comida.model.CabeceraCompraComida;
import com.api.nico.api_papas_nico.compra_comida.model.DetalleCompraComida;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CompraComidaMapper {

    public CompraComidaResponseDTO toResponseDTO(CabeceraCompraComida cabecera, List<DetalleCompraComida> detalles) {
        CompraComidaResponseDTO dto = new CompraComidaResponseDTO();
        dto.setId(cabecera.getId());
        dto.setFechaCompra(cabecera.getFechaCompra());
        dto.setTotal(cabecera.getTotal());

        List<DetalleCompraResponseDTO> detallesDTO = detalles.stream().map(detalle -> {
            DetalleCompraResponseDTO detDTO = new DetalleCompraResponseDTO();
            detDTO.setId(detalle.getId());
            detDTO.setComidaId(detalle.getComidaId().getId());
            detDTO.setNombreComida(detalle.getComidaId().getNombre());
            detDTO.setCantidad(detalle.getCantidad());
            detDTO.setSubtotal(detalle.getComidaId().getPrecio() * detalle.getCantidad());
            return detDTO;
        }).collect(Collectors.toList());

        dto.setDetalles(detallesDTO);
        return dto;
    }

}

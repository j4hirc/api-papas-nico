package com.api.nico.api_papas_nico.compra_materia_prima.dto.response;

import lombok.Data;

import java.util.List;

@Data
public class CompraMateriaPrimaResponseDTO {
    private Long id;
    private String fechaCompra;
    private Double total;
    private List<DetalleCompraMPResponseDTO> detalles;
}

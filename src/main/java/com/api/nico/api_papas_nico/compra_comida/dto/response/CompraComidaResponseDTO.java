package com.api.nico.api_papas_nico.compra_comida.dto.response;

import lombok.Data;

import java.util.List;

@Data
public class CompraComidaResponseDTO {
    private Long id;
    private String fechaCompra;
    private Double total;
    private List<DetalleCompraResponseDTO> detalles;
}

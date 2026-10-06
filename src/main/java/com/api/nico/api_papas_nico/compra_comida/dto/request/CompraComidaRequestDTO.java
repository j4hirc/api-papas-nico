package com.api.nico.api_papas_nico.compra_comida.dto.request;

import lombok.Data;

import java.util.List;

@Data
public class CompraComidaRequestDTO {
    private String fechaCompra;
    private List<DetalleCompraRequestDTO> detalles;
}

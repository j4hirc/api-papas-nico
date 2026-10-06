package com.api.nico.api_papas_nico.compra_materia_prima.dto.request;

import lombok.Data;

import java.util.List;

@Data
public class CompraMateriaPrimaRequestDTO {
    private String fechaCompra;
    private List<DetalleCompraMPRequestDTO> detalles;
}

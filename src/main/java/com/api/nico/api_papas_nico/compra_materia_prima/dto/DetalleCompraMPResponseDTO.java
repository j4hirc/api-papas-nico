package com.api.nico.api_papas_nico.compra_materia_prima.dto;

import lombok.Data;

@Data
public class DetalleCompraMPResponseDTO {
    private Long id;
    private Long materiaPrimaId;
    private String nombreMateriaPrima;
    private Integer cantidad;
    private Double subtotal;
}

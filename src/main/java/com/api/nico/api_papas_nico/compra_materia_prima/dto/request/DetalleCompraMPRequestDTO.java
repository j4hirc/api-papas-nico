package com.api.nico.api_papas_nico.compra_materia_prima.dto.request;

import lombok.Data;

@Data
public class DetalleCompraMPRequestDTO {
    private Long materiaPrimaId;
    private Integer cantidad;
}

package com.api.nico.api_papas_nico.materia_prima.dto;

import java.math.BigDecimal;
import lombok.Data;

@Data
public class MateriaPrimaResponseDTO {

    private Long id;
    private String nombre;
    private BigDecimal precio;
}
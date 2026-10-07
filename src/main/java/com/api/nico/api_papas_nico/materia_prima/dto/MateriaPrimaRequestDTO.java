package com.api.nico.api_papas_nico.materia_prima.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import lombok.Data;

@Data
public class MateriaPrimaRequestDTO {

    @NotBlank
    @Size(max = 255)
    private String nombre;

    @DecimalMin("0.00")
    @Digits(integer = 12, fraction = 2)
    private BigDecimal precio;
}
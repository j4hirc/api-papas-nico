package com.api.nico.api_papas_nico.comidas.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import lombok.Data;

@Data
public class ComidaRequestDTO {

    @NotBlank
    @Size(max = 255)
    private String nombre;

    @NotNull
    @DecimalMin("0.01")
    @Digits(integer = 12, fraction = 2)
    private BigDecimal precio;
}
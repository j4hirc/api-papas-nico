package com.api.nico.api_papas_nico.compra_materia_prima.dto.request;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import lombok.Data;

@Data
public class DetalleCompraMPRequestDTO {

    @NotNull
    @Positive
    private Long materiaPrimaId;

    @NotNull
    @DecimalMin("0.001")
    @Digits(integer = 9, fraction = 3)
    private BigDecimal cantidad;

    @NotBlank
    @Size(max = 40)
    private String unidad;

    @NotNull
    @DecimalMin("0.00")
    @Digits(integer = 12, fraction = 2)
    private BigDecimal importePagado;
}
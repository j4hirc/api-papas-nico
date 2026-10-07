package com.api.nico.api_papas_nico.compra_comida.dto.request;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class DetalleCompraRequestDTO {

    @NotNull
    @Positive
    private Long comidaId;

    @NotNull
    @Min(1)
    @Max(1000000)
    private Integer cantidad;
}
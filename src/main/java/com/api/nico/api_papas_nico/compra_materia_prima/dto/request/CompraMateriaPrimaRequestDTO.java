package com.api.nico.api_papas_nico.compra_materia_prima.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import java.util.List;
import lombok.Data;

@Data
public class CompraMateriaPrimaRequestDTO {

    private String fechaCompra;

    @NotEmpty
    @Size(max = 200)
    @Valid
    private List<@NotNull DetalleCompraMPRequestDTO> detalles;
}
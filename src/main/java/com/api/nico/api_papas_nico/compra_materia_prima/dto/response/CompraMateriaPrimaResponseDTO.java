package com.api.nico.api_papas_nico.compra_materia_prima.dto.response;

import java.math.BigDecimal;
import java.util.List;
import lombok.Data;

@Data
public class CompraMateriaPrimaResponseDTO {

    private Long id;
    private String fechaCompra;
    private boolean horaConocida;
    private BigDecimal total;
    private List<DetalleCompraMPResponseDTO> detalles;
}
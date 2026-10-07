package com.api.nico.api_papas_nico.compra_comida.dto.response;

import java.math.BigDecimal;
import java.util.List;
import lombok.Data;

@Data
public class CompraComidaResponseDTO {

    private Long id;
    private String fechaCompra;
    private boolean horaConocida;
    private BigDecimal total;
    private List<DetalleCompraResponseDTO> detalles;
}
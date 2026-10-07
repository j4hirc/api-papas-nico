package com.api.nico.api_papas_nico.compra_comida.dto.response;

import java.math.BigDecimal;
import lombok.Data;

@Data
public class DetalleCompraResponseDTO {

    private Long id;
    private Long comidaId;
    private String nombreComida;
    private Integer cantidad;
    private BigDecimal precioUnitario;
    private BigDecimal subtotal;
}
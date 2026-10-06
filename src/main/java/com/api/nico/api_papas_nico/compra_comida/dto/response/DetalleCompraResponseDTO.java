package com.api.nico.api_papas_nico.compra_comida.dto.response;

import lombok.Data;

@Data
public class DetalleCompraResponseDTO {
    private Long id;
    private Long comidaId;
    private String nombreComida;
    private Integer cantidad;
    private Double subtotal;
}

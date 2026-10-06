package com.api.nico.api_papas_nico.compra_comida.dto;

import lombok.Data;

@Data
public class DetalleCompraRequestDTO {
    private Long comidaId;
    private Integer cantidad;
}

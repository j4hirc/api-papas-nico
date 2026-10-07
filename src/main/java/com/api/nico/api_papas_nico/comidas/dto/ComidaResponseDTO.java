package com.api.nico.api_papas_nico.comidas.dto;

import java.math.BigDecimal;
import lombok.Data;

@Data
public class ComidaResponseDTO {

    private Long id;
    private String nombre;
    private String foto_url;
    private BigDecimal precio;
}
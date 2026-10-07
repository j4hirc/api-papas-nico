package com.api.nico.api_papas_nico.compra_materia_prima.dto.response;

import java.math.BigDecimal;
import lombok.Data;

@Data
public class DetalleCompraMPResponseDTO {

    private Long id;
    private Long materiaPrimaId;
    private String nombreMateriaPrima;
    private BigDecimal cantidad;
    private String unidad;
    private BigDecimal importePagado;
    private BigDecimal subtotal;
}
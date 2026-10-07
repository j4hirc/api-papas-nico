package com.api.nico.api_papas_nico.compra_comida.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.*;
import lombok.*;

@Entity(name = "cabecera_compra_comida")
@Getter
@Setter
@NoArgsConstructor
public class CabeceraCompraComida {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fechaCompra;
    private LocalDate fecha;
    private LocalTime hora;
    private BigDecimal total;
}
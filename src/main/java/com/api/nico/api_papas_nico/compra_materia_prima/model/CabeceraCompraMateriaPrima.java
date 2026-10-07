package com.api.nico.api_papas_nico.compra_materia_prima.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.*;
import lombok.*;

@Entity(name = "cabecera_compra_materia_prima")
@Getter
@Setter
@NoArgsConstructor
public class CabeceraCompraMateriaPrima {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fechaCompra;
    private LocalDate fecha;
    private LocalTime hora;
    private BigDecimal total;
}
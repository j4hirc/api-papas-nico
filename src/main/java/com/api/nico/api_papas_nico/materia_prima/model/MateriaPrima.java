package com.api.nico.api_papas_nico.materia_prima.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import lombok.*;

@Entity(name = "materia_prima")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MateriaPrima {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 255)
    private String nombre;

    @DecimalMin("0.00")
    @Digits(integer = 12, fraction = 2)
    private BigDecimal precio;
}
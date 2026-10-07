package com.api.nico.api_papas_nico.comidas.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import lombok.*;

@Entity(name = "comidas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Comidas {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 255)
    private String nombre;

    @Column(name = "foto_url")
    private String foto_url;

    @NotNull
    @DecimalMin("0.01")
    @Digits(integer = 12, fraction = 2)
    private BigDecimal precio;
}
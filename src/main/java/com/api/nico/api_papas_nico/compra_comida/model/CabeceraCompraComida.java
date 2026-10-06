package com.api.nico.api_papas_nico.compra_comida.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "cabecera_compra_comida")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CabeceraCompraComida {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fechaCompra;

    private Double total;
}

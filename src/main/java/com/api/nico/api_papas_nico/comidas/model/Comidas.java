package com.api.nico.api_papas_nico.comidas.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "comidas")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Comidas {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    @Column(name = "foto_url")
    private String foto_url;

    private double precio;


}

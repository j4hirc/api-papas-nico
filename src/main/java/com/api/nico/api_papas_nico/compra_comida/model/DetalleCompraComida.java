package com.api.nico.api_papas_nico.compra_comida.model;

import com.api.nico.api_papas_nico.comidas.model.Comidas;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "detalle_compra_comida")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DetalleCompraComida {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cabecera_compra_comida_id", nullable = false)
    private CabeceraCompraComida cabeceraCompra;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comida_id", nullable = false)
    private Comidas comidaId;

    private Integer cantidad;
}

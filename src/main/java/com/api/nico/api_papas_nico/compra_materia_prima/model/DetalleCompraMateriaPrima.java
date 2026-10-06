package com.api.nico.api_papas_nico.compra_materia_prima.model;

import com.api.nico.api_papas_nico.materia_prima.model.MateriaPrima;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "detalle_compra_materia_prima")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DetalleCompraMateriaPrima {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cabecera_compra_materia_prima", nullable = false)
    private CabeceraCompraMateriaPrima cabeceraCompra;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "materia_prima_id", nullable = false)
    private MateriaPrima materiaPrima;

    private Integer cantidad;
}
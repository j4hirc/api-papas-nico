package com.api.nico.api_papas_nico.compra_materia_prima.repository;

import com.api.nico.api_papas_nico.compra_materia_prima.model.DetalleCompraMateriaPrima;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DetalleCompraMPRepository extends JpaRepository<DetalleCompraMateriaPrima, Long> {
    List<DetalleCompraMateriaPrima> findByCabeceraCompra_Id(Long cabeceraId);
}
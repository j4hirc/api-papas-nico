package com.api.nico.api_papas_nico.compra_materia_prima.repository;

import com.api.nico.api_papas_nico.compra_materia_prima.model.CabeceraCompraMateriaPrima;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CabeceraCompraMPRepository extends JpaRepository<CabeceraCompraMateriaPrima, Long> {
}
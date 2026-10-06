package com.api.nico.api_papas_nico.compra_comida.repository;

import com.api.nico.api_papas_nico.compra_comida.model.CabeceraCompraComida;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CabeceraCompraRepository extends JpaRepository<CabeceraCompraComida, Long> {
}
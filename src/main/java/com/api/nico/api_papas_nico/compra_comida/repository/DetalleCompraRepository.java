package com.api.nico.api_papas_nico.compra_comida.repository;

import com.api.nico.api_papas_nico.compra_comida.model.DetalleCompraComida;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DetalleCompraRepository extends JpaRepository<DetalleCompraComida, Long> {
    List<DetalleCompraComida> findByCabeceraCompra_Id(Long cabeceraId);
}
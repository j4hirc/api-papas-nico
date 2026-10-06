package com.api.nico.api_papas_nico.compra_materia_prima.service;

import com.api.nico.api_papas_nico.compra_materia_prima.dto.CompraMateriaPrimaRequestDTO;
import com.api.nico.api_papas_nico.compra_materia_prima.dto.CompraMateriaPrimaResponseDTO;
import com.api.nico.api_papas_nico.compra_materia_prima.dto.DetalleCompraMPRequestDTO;

import java.util.List;

public interface CompraMateriaPrimaService {

    CompraMateriaPrimaResponseDTO createCompra(CompraMateriaPrimaRequestDTO requestDTO);

    CompraMateriaPrimaResponseDTO getCompraById(Long cabeceraId);
    List<CompraMateriaPrimaResponseDTO> getAllCompras();
    void deleteCompra(Long cabeceraId);
}

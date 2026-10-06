package com.api.nico.api_papas_nico.compra_comida.service;


import com.api.nico.api_papas_nico.compra_comida.dto.CompraComidaRequestDTO;
import com.api.nico.api_papas_nico.compra_comida.dto.CompraComidaResponseDTO;

import java.util.List;

public interface CompraComidaService {
    CompraComidaResponseDTO createCompra(CompraComidaRequestDTO requestDTO);
    CompraComidaResponseDTO getCompraById(Long id);
    List<CompraComidaResponseDTO> getAllCompras();
    void deleteCompra(Long id);
}

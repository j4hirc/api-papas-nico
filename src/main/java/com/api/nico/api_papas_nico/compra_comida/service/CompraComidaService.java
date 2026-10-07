package com.api.nico.api_papas_nico.compra_comida.service;

import com.api.nico.api_papas_nico.compra_comida.dto.request.CompraComidaRequestDTO;
import com.api.nico.api_papas_nico.compra_comida.dto.response.CompraComidaResponseDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

public interface CompraComidaService {

    CompraComidaResponseDTO createCompra(
            @NotNull @Valid CompraComidaRequestDTO dto
    );

    CompraComidaResponseDTO updateCompra(
            Long id,
            @NotNull @Valid CompraComidaRequestDTO dto
    );

    CompraComidaResponseDTO getCompraById(Long id);

    List<CompraComidaResponseDTO> getAllCompras(
            LocalDate desde,
            LocalDate hasta
    );

    void deleteCompra(Long id);
}
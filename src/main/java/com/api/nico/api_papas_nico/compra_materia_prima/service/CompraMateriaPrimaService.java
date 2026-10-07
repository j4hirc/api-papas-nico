package com.api.nico.api_papas_nico.compra_materia_prima.service;

import com.api.nico.api_papas_nico.compra_materia_prima.dto.request.CompraMateriaPrimaRequestDTO;
import com.api.nico.api_papas_nico.compra_materia_prima.dto.response.CompraMateriaPrimaResponseDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

public interface CompraMateriaPrimaService {

    CompraMateriaPrimaResponseDTO createCompra(
            @NotNull @Valid CompraMateriaPrimaRequestDTO dto
    );

    CompraMateriaPrimaResponseDTO updateCompra(
            Long id,
            @NotNull @Valid CompraMateriaPrimaRequestDTO dto
    );

    CompraMateriaPrimaResponseDTO getCompraById(Long id);

    List<CompraMateriaPrimaResponseDTO> getAllCompras(
            LocalDate desde,
            LocalDate hasta
    );

    void deleteCompra(Long id);
}
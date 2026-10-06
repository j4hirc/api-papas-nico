package com.api.nico.api_papas_nico.materia_prima.service;

import com.api.nico.api_papas_nico.materia_prima.dto.MateriaPrimaRequestDTO;
import com.api.nico.api_papas_nico.materia_prima.dto.MateriaPrimaResponseDTO;

import java.util.List;

public interface MateriaPrimaService {

    List<MateriaPrimaResponseDTO> getAllMateriaPrima();

    MateriaPrimaResponseDTO getMateriaPrimaById(Long id);

    MateriaPrimaResponseDTO createMateriaPrima(MateriaPrimaRequestDTO materiaPrimaRequestDTO);

    MateriaPrimaResponseDTO updateMateriaPrima(Long id, MateriaPrimaRequestDTO materiaPrimaRequestDTO);

    MateriaPrimaResponseDTO deleteMateriaPrima(Long id);


}

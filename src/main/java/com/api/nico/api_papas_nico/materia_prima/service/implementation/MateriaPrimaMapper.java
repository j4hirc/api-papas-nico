package com.api.nico.api_papas_nico.materia_prima.service.implementation;

import com.api.nico.api_papas_nico.materia_prima.dto.MateriaPrimaRequestDTO;
import com.api.nico.api_papas_nico.materia_prima.dto.MateriaPrimaResponseDTO;
import com.api.nico.api_papas_nico.materia_prima.model.MateriaPrima;
import org.springframework.stereotype.Component;

@Component
public class MateriaPrimaMapper {

    public MateriaPrimaResponseDTO toResponseDTO(MateriaPrima materiaPrima) {
        MateriaPrimaResponseDTO responseDTO = new MateriaPrimaResponseDTO();
        responseDTO.setId(materiaPrima.getId());
        responseDTO.setNombre(materiaPrima.getNombre());
        responseDTO.setPrecio(materiaPrima.getPrecio());
        return responseDTO;
    }

    public MateriaPrima toEntity(MateriaPrimaRequestDTO requestDTO) {
        MateriaPrima materiaPrima = new MateriaPrima();
        materiaPrima.setNombre(requestDTO.getNombre());
        materiaPrima.setPrecio(requestDTO.getPrecio());
        return materiaPrima;
    }

}

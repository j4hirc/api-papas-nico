package com.api.nico.api_papas_nico.comidas.service.implementation;

import com.api.nico.api_papas_nico.comidas.dto.ComidaRequestDTO;
import com.api.nico.api_papas_nico.comidas.dto.ComidaResponseDTO;
import com.api.nico.api_papas_nico.comidas.model.Comidas;
import org.springframework.stereotype.Component;

@Component
public class ComidaMapper {

    public ComidaResponseDTO toResponseDTO(Comidas comida) {
        ComidaResponseDTO responseDTO = new ComidaResponseDTO();
        responseDTO.setId(comida.getId());
        responseDTO.setNombre(comida.getNombre());
        responseDTO.setFoto_url(comida.getFoto_url());
        responseDTO.setPrecio(comida.getPrecio());
        return responseDTO;
    }

    public Comidas toEntity(ComidaRequestDTO requestDTO) {
        Comidas comida = new Comidas();
        comida.setNombre(requestDTO.getNombre());
        comida.setPrecio(requestDTO.getPrecio());
        return comida;
    }

}

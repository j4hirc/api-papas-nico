package com.api.nico.api_papas_nico.comidas.service;

import com.api.nico.api_papas_nico.comidas.dto.ComidaRequestDTO;
import com.api.nico.api_papas_nico.comidas.dto.ComidaResponseDTO;
import com.api.nico.api_papas_nico.comidas.model.Comidas;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ComidaService {

    List<ComidaResponseDTO> getAllComidas();

    ComidaResponseDTO getComidaById(Long id);

    ComidaResponseDTO createComida(ComidaRequestDTO comidaRequestDTO, MultipartFile imagen);

    ComidaResponseDTO updateComida(Long id, ComidaRequestDTO comidaRequestDTO, MultipartFile imagen);

    ComidaResponseDTO deleteComida(Long id);

}

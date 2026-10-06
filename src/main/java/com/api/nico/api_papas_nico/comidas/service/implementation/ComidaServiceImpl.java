package com.api.nico.api_papas_nico.comidas.service.implementation;

import com.api.nico.api_papas_nico.comidas.dto.ComidaRequestDTO;
import com.api.nico.api_papas_nico.comidas.dto.ComidaResponseDTO;
import com.api.nico.api_papas_nico.comidas.model.Comidas;
import com.api.nico.api_papas_nico.comidas.repository.ComidaRepository;
import com.api.nico.api_papas_nico.comidas.service.ComidaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ComidaServiceImpl implements ComidaService {

    private final ComidaRepository comidaRepository;
    private final ComidaMapper comidaMapper;
    private final SupabaseStorageService storageService;


    @Override
    public List<ComidaResponseDTO> getAllComidas() {
        List<Comidas> comidas = comidaRepository.findAll();
        return comidas.stream()
                .map(comidaMapper::toResponseDTO)
                .toList();
    }

    @Override
    public ComidaResponseDTO getComidaById(Long id) {
        Comidas comidas = comidaRepository.findById(id).
                orElseThrow(() -> new RuntimeException("Comida no encontrada con id: " + id));

        return comidaMapper.toResponseDTO(comidas);
    }

    @Override
    public ComidaResponseDTO createComida(ComidaRequestDTO comidaRequestDTO, MultipartFile imagen) {

        Comidas comida = comidaMapper.toEntity(comidaRequestDTO);

        if (imagen != null && !imagen.isEmpty()) {
            try {
                String urlImagen = storageService.uploadFile(imagen, "productos");
                comida.setFoto_url(urlImagen);
            } catch (Exception e) {
                throw new RuntimeException("Error al subir la imagen del producto: " + e.getMessage());
            }
        }
        Comidas comidaSaved = comidaRepository.save(comida);

        return comidaMapper.toResponseDTO(comidaSaved);
    }

    @Override
    public ComidaResponseDTO updateComida(Long id, ComidaRequestDTO comidaRequestDTO, MultipartFile imagen) {

        Comidas comidas = comidaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Comida no encontrada con id: " + id));

        comidas.setNombre(comidaRequestDTO.getNombre());
        comidas.setPrecio(comidaRequestDTO.getPrecio());

        if (imagen != null && !imagen.isEmpty()) {
            try {
                String urlImagen = storageService.uploadFile(imagen, "productos");
                comidas.setFoto_url(urlImagen);
            } catch (Exception e) {
                throw new RuntimeException("Error al subir la nueva imagen: " + e.getMessage());
            }
        }

        Comidas comidaActualizada = comidaRepository.save(comidas);

        return comidaMapper.toResponseDTO(comidaActualizada);
    }

    @Override
    public ComidaResponseDTO deleteComida(Long id) {
        Comidas comida = comidaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Comida no encontrada con id: " + id));

        comidaRepository.delete(comida);

        return comidaMapper.toResponseDTO(comida);
    }
}

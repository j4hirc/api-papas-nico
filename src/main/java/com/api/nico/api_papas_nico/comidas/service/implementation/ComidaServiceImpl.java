package com.api.nico.api_papas_nico.comidas.service.implementation;

import com.api.nico.api_papas_nico.comidas.dto.*;
import com.api.nico.api_papas_nico.comidas.model.Comidas;
import com.api.nico.api_papas_nico.comidas.repository.ComidaRepository;
import com.api.nico.api_papas_nico.comidas.service.ComidaService;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
@Transactional
public class ComidaServiceImpl implements ComidaService {

    private final ComidaRepository repository;
    private final ComidaMapper mapper;
    private final SupabaseStorageService storage;

    @Override
    @Transactional(readOnly = true)
    public List<ComidaResponseDTO> getAllComidas() {
        return repository.findAll()
                .stream()
                .map(mapper::toResponseDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public ComidaResponseDTO getComidaById(Long id) {
        return mapper.toResponseDTO(buscar(id));
    }

    @Override
    public ComidaResponseDTO createComida(
            ComidaRequestDTO dto,
            MultipartFile imagen
    ) {
        var c = mapper.toEntity(dto);

        if (imagen != null && !imagen.isEmpty()) {
            try {
                c.setFoto_url(storage.uploadFile(imagen, "productos"));
            } catch (IOException e) {
                throw new ResponseStatusException(
                        HttpStatus.BAD_GATEWAY,
                        "No se pudo subir la imagen",
                        e
                );
            }
        }

        return mapper.toResponseDTO(repository.save(c));
    }

    @Override
    public ComidaResponseDTO updateComida(
            Long id,
            ComidaRequestDTO dto,
            MultipartFile imagen
    ) {
        var c = buscar(id);
        c.setNombre(dto.getNombre());
        c.setPrecio(dto.getPrecio());

        if (imagen != null && !imagen.isEmpty()) {
            try {
                c.setFoto_url(storage.uploadFile(imagen, "productos"));
            } catch (IOException e) {
                throw new ResponseStatusException(
                        HttpStatus.BAD_GATEWAY,
                        "No se pudo subir la imagen",
                        e
                );
            }
        }

        return mapper.toResponseDTO(repository.save(c));
    }

    @Override
    public ComidaResponseDTO deleteComida(Long id) {
        var c = buscar(id);
        var respuesta = mapper.toResponseDTO(c);

        repository.delete(c);
        repository.flush();

        return respuesta;
    }

    private Comidas buscar(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Producto inexistente: " + id
                ));
    }
}
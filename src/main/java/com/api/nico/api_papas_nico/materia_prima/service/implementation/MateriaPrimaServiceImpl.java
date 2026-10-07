package com.api.nico.api_papas_nico.materia_prima.service.implementation;

import com.api.nico.api_papas_nico.materia_prima.dto.*;
import com.api.nico.api_papas_nico.materia_prima.model.MateriaPrima;
import com.api.nico.api_papas_nico.materia_prima.repository.MateriaPrimaRepository;
import com.api.nico.api_papas_nico.materia_prima.service.MateriaPrimaService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
@Transactional
public class MateriaPrimaServiceImpl implements MateriaPrimaService {

    private final MateriaPrimaRepository repository;
    private final MateriaPrimaMapper mapper;

    @Override
    @Transactional(readOnly = true)
    public List<MateriaPrimaResponseDTO> getAllMateriaPrima() {
        return repository.findAll()
                .stream()
                .map(mapper::toResponseDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public MateriaPrimaResponseDTO getMateriaPrimaById(Long id) {
        return mapper.toResponseDTO(buscar(id));
    }

    @Override
    public MateriaPrimaResponseDTO createMateriaPrima(
            MateriaPrimaRequestDTO dto
    ) {
        var c = mapper.toEntity(dto);
        return mapper.toResponseDTO(repository.save(c));
    }

    @Override
    public MateriaPrimaResponseDTO updateMateriaPrima(
            Long id,
            MateriaPrimaRequestDTO dto
    ) {
        var c = buscar(id);
        c.setNombre(dto.getNombre());
        c.setPrecio(dto.getPrecio());

        return mapper.toResponseDTO(repository.save(c));
    }

    @Override
    public MateriaPrimaResponseDTO deleteMateriaPrima(Long id) {
        var c = buscar(id);
        var respuesta = mapper.toResponseDTO(c);

        repository.delete(c);
        repository.flush();

        return respuesta;
    }

    private MateriaPrima buscar(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Producto inexistente: " + id
                ));
    }
}
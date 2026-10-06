package com.api.nico.api_papas_nico.materia_prima.service.implementation;


import com.api.nico.api_papas_nico.materia_prima.dto.MateriaPrimaRequestDTO;
import com.api.nico.api_papas_nico.materia_prima.dto.MateriaPrimaResponseDTO;
import com.api.nico.api_papas_nico.materia_prima.model.MateriaPrima;
import com.api.nico.api_papas_nico.materia_prima.repository.MateriaPrimaRepository;
import com.api.nico.api_papas_nico.materia_prima.service.MateriaPrimaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MateriaPrimaServiceImpl implements MateriaPrimaService {

    private final MateriaPrimaRepository materiaPrimaRepository;
    private final MateriaPrimaMapper materiaPrimaMapper;


    @Override
    public List<MateriaPrimaResponseDTO> getAllMateriaPrima() {
        List<MateriaPrima> materiaPrimaList = materiaPrimaRepository.findAll();
        return materiaPrimaList.stream()
                .map(materiaPrimaMapper::toResponseDTO)
                .toList();
    }

    @Override
    public MateriaPrimaResponseDTO getMateriaPrimaById(Long id) {
        MateriaPrima materiaPrima = materiaPrimaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Materia Prima not found with id: " + id));

        return materiaPrimaMapper.toResponseDTO(materiaPrima);
    }

    @Override
    public MateriaPrimaResponseDTO createMateriaPrima(MateriaPrimaRequestDTO materiaPrimaRequestDTO) {

        MateriaPrima materiaPrima = materiaPrimaMapper.toEntity(materiaPrimaRequestDTO);
        MateriaPrima savedMateriaPrima = materiaPrimaRepository.save(materiaPrima);
        return materiaPrimaMapper.toResponseDTO(savedMateriaPrima);
    }

    @Override
    public MateriaPrimaResponseDTO updateMateriaPrima(Long id, MateriaPrimaRequestDTO materiaPrimaRequestDTO) {
        MateriaPrima existingMateriaPrima = materiaPrimaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Materia Prima not found with id: " + id));

        existingMateriaPrima.setPrecio(materiaPrimaRequestDTO.getPrecio());
        existingMateriaPrima.setNombre(materiaPrimaRequestDTO.getNombre());
        MateriaPrima updatedMateriaPrima = materiaPrimaRepository.save(existingMateriaPrima);

        return materiaPrimaMapper.toResponseDTO(updatedMateriaPrima);
    }

    @Override
    public MateriaPrimaResponseDTO deleteMateriaPrima(Long id) {
        MateriaPrima existingMateriaPrima = materiaPrimaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Materia Prima not found with id: " + id));
        materiaPrimaRepository.delete(existingMateriaPrima);
        return materiaPrimaMapper.toResponseDTO(existingMateriaPrima);
    }
}

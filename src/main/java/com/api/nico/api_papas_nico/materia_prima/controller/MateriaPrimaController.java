package com.api.nico.api_papas_nico.materia_prima.controller;

import com.api.nico.api_papas_nico.materia_prima.dto.MateriaPrimaRequestDTO;
import com.api.nico.api_papas_nico.materia_prima.dto.MateriaPrimaResponseDTO;
import com.api.nico.api_papas_nico.materia_prima.service.MateriaPrimaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api/materia-prima")
@RequiredArgsConstructor
public class MateriaPrimaController {

    private final MateriaPrimaService materiaPrimaService;

    @GetMapping
    public ResponseEntity<List<MateriaPrimaResponseDTO>> getAllMateriaPrima() {
        return ResponseEntity.ok(materiaPrimaService.getAllMateriaPrima());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MateriaPrimaResponseDTO> getMateriaPrimaById(@PathVariable Long id) {
        return ResponseEntity.ok(materiaPrimaService.getMateriaPrimaById(id));
    }

    @PostMapping
    public ResponseEntity<MateriaPrimaResponseDTO> createMateriaPrima(
            @Valid @RequestBody MateriaPrimaRequestDTO materiaPrimaRequestDTO
    ) {
        MateriaPrimaResponseDTO nuevaMateriaPrima =
                materiaPrimaService.createMateriaPrima(materiaPrimaRequestDTO);

        return new ResponseEntity<>(nuevaMateriaPrima, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MateriaPrimaResponseDTO> updateMateriaPrima(
            @PathVariable Long id,
            @Valid @RequestBody MateriaPrimaRequestDTO materiaPrimaRequestDTO
    ) {
        MateriaPrimaResponseDTO materiaPrimaActualizada =
                materiaPrimaService.updateMateriaPrima(id, materiaPrimaRequestDTO);

        return ResponseEntity.ok(materiaPrimaActualizada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MateriaPrimaResponseDTO> deleteMateriaPrima(@PathVariable Long id) {
        return ResponseEntity.ok(materiaPrimaService.deleteMateriaPrima(id));
    }
}
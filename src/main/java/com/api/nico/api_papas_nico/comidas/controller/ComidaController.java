package com.api.nico.api_papas_nico.comidas.controller;

import com.api.nico.api_papas_nico.comidas.dto.ComidaRequestDTO;
import com.api.nico.api_papas_nico.comidas.dto.ComidaResponseDTO;
import com.api.nico.api_papas_nico.comidas.service.ComidaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/comidas")
@RequiredArgsConstructor
public class ComidaController {

    private final ComidaService comidaService;

    @GetMapping
    public ResponseEntity<List<ComidaResponseDTO>> getAllComidas() {
        return ResponseEntity.ok(comidaService.getAllComidas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ComidaResponseDTO> getComidaById(@PathVariable Long id) {
        return ResponseEntity.ok(comidaService.getComidaById(id));
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ComidaResponseDTO> createComida(
            @RequestPart("comida") ComidaRequestDTO comidaRequestDTO,
            @RequestPart(value = "imagen", required = false) MultipartFile imagen) {

        ComidaResponseDTO nuevaComida = comidaService.createComida(comidaRequestDTO, imagen);
        return new ResponseEntity<>(nuevaComida, HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ComidaResponseDTO> updateComida(
            @PathVariable Long id,
            @RequestPart("comida") ComidaRequestDTO comidaRequestDTO,
            @RequestPart(value = "imagen", required = false) MultipartFile imagen) {

        ComidaResponseDTO comidaActualizada = comidaService.updateComida(id, comidaRequestDTO, imagen);
        return ResponseEntity.ok(comidaActualizada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ComidaResponseDTO> deleteComida(@PathVariable Long id) {
        return ResponseEntity.ok(comidaService.deleteComida(id));
    }
}
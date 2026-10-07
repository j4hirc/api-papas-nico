package com.api.nico.api_papas_nico.compra_materia_prima.controller;

import com.api.nico.api_papas_nico.compra_materia_prima.dto.request.CompraMateriaPrimaRequestDTO;
import com.api.nico.api_papas_nico.compra_materia_prima.dto.response.CompraMateriaPrimaResponseDTO;
import com.api.nico.api_papas_nico.compra_materia_prima.service.CompraMateriaPrimaService;
import jakarta.validation.Valid;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/compras-mp")
public class CompraMateriaPrimaController {

    private final CompraMateriaPrimaService servicio;

    @PostMapping
    public ResponseEntity<CompraMateriaPrimaResponseDTO> crear(
            @Valid @RequestBody CompraMateriaPrimaRequestDTO dto
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(servicio.createCompra(dto));
    }

    @PutMapping("/{id}")
    public CompraMateriaPrimaResponseDTO editar(
            @PathVariable Long id,
            @Valid @RequestBody CompraMateriaPrimaRequestDTO dto
    ) {
        return servicio.updateCompra(id, dto);
    }

    @GetMapping("/{id}")
    public CompraMateriaPrimaResponseDTO obtener(@PathVariable Long id) {
        return servicio.getCompraById(id);
    }

    @GetMapping
    public List<CompraMateriaPrimaResponseDTO> listar(
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate desde,

            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate hasta
    ) {
        return servicio.getAllCompras(desde, hasta);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        servicio.deleteCompra(id);
        return ResponseEntity.noContent().build();
    }
}
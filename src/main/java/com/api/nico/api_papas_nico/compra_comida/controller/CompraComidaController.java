package com.api.nico.api_papas_nico.compra_comida.controller;

import com.api.nico.api_papas_nico.compra_comida.dto.request.CompraComidaRequestDTO;
import com.api.nico.api_papas_nico.compra_comida.dto.response.CompraComidaResponseDTO;
import com.api.nico.api_papas_nico.compra_comida.service.CompraComidaService;
import jakarta.validation.Valid;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping({"/api/compras-comida", "/api/ventas"})
public class CompraComidaController {

    private final CompraComidaService servicio;

    @PostMapping
    public ResponseEntity<CompraComidaResponseDTO> crear(
            @Valid @RequestBody CompraComidaRequestDTO dto
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(servicio.createCompra(dto));
    }

    @PutMapping("/{id}")
    public CompraComidaResponseDTO editar(
            @PathVariable Long id,
            @Valid @RequestBody CompraComidaRequestDTO dto
    ) {
        return servicio.updateCompra(id, dto);
    }

    @GetMapping("/{id}")
    public CompraComidaResponseDTO obtener(@PathVariable Long id) {
        return servicio.getCompraById(id);
    }

    @GetMapping
    public List<CompraComidaResponseDTO> listar(
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
package com.api.nico.api_papas_nico.compra_materia_prima.controller;

import com.api.nico.api_papas_nico.compra_materia_prima.dto.request.CompraMateriaPrimaRequestDTO;
import com.api.nico.api_papas_nico.compra_materia_prima.dto.response.CompraMateriaPrimaResponseDTO;
import com.api.nico.api_papas_nico.compra_materia_prima.service.CompraMateriaPrimaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/compras-mp")
@RequiredArgsConstructor
public class CompraMateriaPrimaController {

    private final CompraMateriaPrimaService compraService;

    @PostMapping
    public ResponseEntity<CompraMateriaPrimaResponseDTO> createCompra(@RequestBody CompraMateriaPrimaRequestDTO dto) {
        return new ResponseEntity<>(compraService.createCompra(dto), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<CompraMateriaPrimaResponseDTO>> getAllCompras() {
        return ResponseEntity.ok(compraService.getAllCompras());
    }

    @GetMapping("/{cabeceraId}")
    public ResponseEntity<CompraMateriaPrimaResponseDTO> getCompraById(@PathVariable Long cabeceraId) {
        return ResponseEntity.ok(compraService.getCompraById(cabeceraId));
    }

    @DeleteMapping("/{cabeceraId}")
    public ResponseEntity<Void> deleteCompra(@PathVariable Long cabeceraId) {
        compraService.deleteCompra(cabeceraId);
        return ResponseEntity.noContent().build();
    }
}
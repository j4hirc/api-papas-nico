package com.api.nico.api_papas_nico.compra_comida.controller;

import com.api.nico.api_papas_nico.compra_comida.dto.request.CompraComidaRequestDTO;
import com.api.nico.api_papas_nico.compra_comida.dto.response.CompraComidaResponseDTO;
import com.api.nico.api_papas_nico.compra_comida.service.CompraComidaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/compras-comida")
@RequiredArgsConstructor
public class CompraComidaController {

    private final CompraComidaService compraService;

    @PostMapping
    public ResponseEntity<CompraComidaResponseDTO> createCompra(@RequestBody CompraComidaRequestDTO requestDTO) {
        return new ResponseEntity<>(compraService.createCompra(requestDTO), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<CompraComidaResponseDTO>> getAllCompras() {
        return ResponseEntity.ok(compraService.getAllCompras());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CompraComidaResponseDTO> getCompraById(@PathVariable Long id) {
        return ResponseEntity.ok(compraService.getCompraById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCompra(@PathVariable Long id) {
        compraService.deleteCompra(id);
        return ResponseEntity.noContent().build();
    }
}

package com.api.nico.api_papas_nico.reportes.controller;

import java.time.LocalDate;

import com.api.nico.api_papas_nico.reportes.service.ReporteService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reportes")
public class ReporteController {

    private final ReporteService servicio;

    @GetMapping
    public ReporteService.Reporte consultar(
            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate desde,

            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate hasta
    ) {
        return servicio.consultar(desde, hasta);
    }
}
package com.api.nico.api_papas_nico.common;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/ping")
public class PingController {

    @GetMapping
    public ResponseEntity<String> ping() {
        // Retorna un simple texto y un código HTTP 200 (OK)
        return ResponseEntity.ok("OK - El backend de Papas Nico está despierto");
    }
}
package com.api.nico.api_papas_nico.common;

import jakarta.validation.ConstraintViolationException;
import java.util.Map;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestControllerAdvice
public class Errores {

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Map<String, String>> validacion(
            ConstraintViolationException e
    ) {
        return ResponseEntity.badRequest()
                .body(Map.of("mensaje", e.getMessage()));
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<Map<String, String>> negocio(
            ResponseStatusException e
    ) {
        return ResponseEntity.status(e.getStatusCode())
                .body(Map.of(
                        "mensaje",
                        e.getReason() == null
                                ? "Solicitud inválida"
                                : e.getReason()
                ));
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Map<String, String>> integridad(
            DataIntegrityViolationException e
    ) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(Map.of(
                        "mensaje",
                        "La operación viola una restricción. "
                                + "No se puede eliminar un producto "
                                + "utilizado en el historial."
                ));
    }
}
package com.api.nico.api_papas_nico.common;

import java.time.*;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public final class Fechas {

    public static final ZoneId ZONA = ZoneId.of("America/Guayaquil");

    private Fechas() {
    }

    public static ZonedDateTime resolver(String valor) {
        if (valor == null) {
            return ZonedDateTime.now(ZONA).truncatedTo(ChronoUnit.MICROS);
        }

        try {
            return OffsetDateTime.parse(valor)
                    .atZoneSameInstant(ZONA)
                    .truncatedTo(ChronoUnit.MICROS);
        } catch (DateTimeParseException e) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "fechaCompra debe incluir fecha, hora y offset; "
                            + "ejemplo: 2026-10-06T10:00:00-05:00"
            );
        }
    }

    public static String mostrar(
            LocalDate fecha,
            LocalTime hora,
            String original
    ) {
        if (fecha == null) {
            return original;
        }

        if (hora == null) {
            return fecha.toString();
        }

        return fecha.atTime(hora)
                .atZone(ZONA)
                .toOffsetDateTime()
                .toString();
    }

    public static void rango(LocalDate desde, LocalDate hasta) {
        if (desde != null && hasta != null && desde.isAfter(hasta)) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "desde no puede superar hasta"
            );
        }
    }
}
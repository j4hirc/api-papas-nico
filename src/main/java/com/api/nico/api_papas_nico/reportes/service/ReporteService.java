package com.api.nico.api_papas_nico.reportes.service;

import com.api.nico.api_papas_nico.common.Fechas;
import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class ReporteService {

    private final JdbcTemplate jdbc;

    public record Dia(
            LocalDate fecha,
            long operaciones,
            long unidades,
            BigDecimal vendido,
            BigDecimal compras,
            BigDecimal balance
    ) {
    }

    public record Producto(
            long comidaId,
            String nombre,
            long unidades
    ) {
    }

    public record Reporte(
            LocalDate desde,
            LocalDate hasta,
            String zonaHoraria,
            BigDecimal totalVendido,
            long unidadesVendidas,
            long operacionesVenta,
            BigDecimal totalCompras,
            BigDecimal balance,
            List<Dia> resumenPorDia,
            List<LocalDate> diasMasUnidades,
            List<LocalDate> diasMayoresIngresos,
            List<Producto> productosMasVendidos,
            Map<String, Long> advertencias
    ) {
    }

    @Transactional(
            readOnly = true,
            isolation = Isolation.REPEATABLE_READ
    )
    public Reporte consultar(LocalDate desde, LocalDate hasta) {
        Fechas.rango(desde, hasta);

        if (ChronoUnit.DAYS.between(desde, hasta) > 3660) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Máximo 3661 días por consulta"
            );
        }

        Date a = Date.valueOf(desde);
        Date z = Date.valueOf(hasta);

        List<Dia> dias = jdbc.query("""
            WITH calendario AS (
                SELECT generate_series(
                    CAST(? AS date),
                    CAST(? AS date),
                    interval '1 day'
                )::date fecha
            ), ventas AS (
                SELECT fecha, count(*) operaciones, sum(total) vendido
                FROM cabecera_compra_comida
                WHERE fecha BETWEEN ? AND ?
                GROUP BY fecha
            ), unidades AS (
                SELECT c.fecha, sum(d.cantidad) cantidad
                FROM cabecera_compra_comida c
                JOIN detalle_compra_comida d
                  ON d.cabecera_compra_comida_id = c.id
                WHERE c.fecha BETWEEN ? AND ?
                GROUP BY c.fecha
            ), compras AS (
                SELECT fecha, sum(total) total
                FROM cabecera_compra_materia_prima
                WHERE fecha BETWEEN ? AND ?
                GROUP BY fecha
            )
            SELECT
                k.fecha,
                coalesce(v.operaciones, 0) operaciones,
                coalesce(u.cantidad, 0) unidades,
                coalesce(v.vendido, 0) vendido,
                coalesce(p.total, 0) compras
            FROM calendario k
            LEFT JOIN ventas v USING(fecha)
            LEFT JOIN unidades u USING(fecha)
            LEFT JOIN compras p USING(fecha)
            ORDER BY k.fecha
            """,
                (rs, n) -> {
                    BigDecimal venta = rs.getBigDecimal("vendido");
                    BigDecimal compra = rs.getBigDecimal("compras");

                    return new Dia(
                            rs.getDate("fecha").toLocalDate(),
                            rs.getLong("operaciones"),
                            rs.getLong("unidades"),
                            venta,
                            compra,
                            venta.subtract(compra)
                    );
                },
                a, z, a, z, a, z, a, z
        );

        List<Producto> productos = jdbc.query("""
            SELECT p.id, p.nombre, sum(d.cantidad) unidades
            FROM detalle_compra_comida d
            JOIN cabecera_compra_comida c
              ON c.id = d.cabecera_compra_comida_id
            JOIN comidas p
              ON p.id = d.comida_id
            WHERE c.fecha BETWEEN ? AND ?
            GROUP BY p.id, p.nombre
            ORDER BY unidades DESC, p.id
            """,
                (rs, n) -> new Producto(
                        rs.getLong("id"),
                        rs.getString("nombre"),
                        rs.getLong("unidades")
                ),
                a, z
        );

        BigDecimal ventas = dias.stream()
                .map(Dia::vendido)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal compras = dias.stream()
                .map(Dia::compras)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        long maxUnidades = dias.stream()
                .mapToLong(Dia::unidades)
                .max()
                .orElse(0);

        BigDecimal maxIngreso = dias.stream()
                .map(Dia::vendido)
                .max(BigDecimal::compareTo)
                .orElse(BigDecimal.ZERO);

        Map<String, Long> avisos = new LinkedHashMap<>();

        avisos.put(
                "ventasSinFechaGlobal",
                contar("""
                    SELECT count(*)
                    FROM cabecera_compra_comida
                    WHERE fecha IS NULL
                    """)
        );

        avisos.put(
                "comprasSinFechaGlobal",
                contar("""
                    SELECT count(*)
                    FROM cabecera_compra_materia_prima
                    WHERE fecha IS NULL
                    """)
        );

        avisos.put(
                "ventasSinHoraPeriodo",
                contar("""
                    SELECT count(*)
                    FROM cabecera_compra_comida
                    WHERE fecha BETWEEN ? AND ?
                      AND hora IS NULL
                    """, a, z)
        );

        avisos.put(
                "lineasVentaSinPrecioHistoricoPeriodo",
                contar("""
                    SELECT count(*)
                    FROM detalle_compra_comida d
                    JOIN cabecera_compra_comida c
                      ON c.id = d.cabecera_compra_comida_id
                    WHERE c.fecha BETWEEN ? AND ?
                      AND d.precio_unitario IS NULL
                    """, a, z)
        );

        avisos.put(
                "lineasCompraSinImporteOUnidadPeriodo",
                contar("""
                    SELECT count(*)
                    FROM detalle_compra_materia_prima d
                    JOIN cabecera_compra_materia_prima c
                      ON c.id = d.cabecera_compra_materia_prima
                    WHERE c.fecha BETWEEN ? AND ?
                      AND (
                          d.importe_pagado IS NULL
                          OR d.unidad IS NULL
                      )
                    """, a, z)
        );

        List<LocalDate> diasMasUnidades = maxUnidades == 0
                ? List.of()
                : dias.stream()
                .filter(d -> d.unidades() == maxUnidades)
                .map(Dia::fecha)
                .toList();

        List<LocalDate> diasMayoresIngresos = maxIngreso.signum() == 0
                ? List.of()
                : dias.stream()
                .filter(d -> d.vendido().compareTo(maxIngreso) == 0)
                .map(Dia::fecha)
                .toList();

        return new Reporte(
                desde,
                hasta,
                Fechas.ZONA.getId(),
                ventas,
                dias.stream().mapToLong(Dia::unidades).sum(),
                dias.stream().mapToLong(Dia::operaciones).sum(),
                compras,
                ventas.subtract(compras),
                dias,
                diasMasUnidades,
                diasMayoresIngresos,
                productos,
                avisos
        );
    }

    private long contar(String sql, Object... args) {
        return Objects.requireNonNull(
                jdbc.queryForObject(sql, Long.class, args)
        );
    }
}
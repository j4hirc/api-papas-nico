package com.api.nico.api_papas_nico.compra_materia_prima.service.implementation;

import com.api.nico.api_papas_nico.common.Fechas;
import com.api.nico.api_papas_nico.compra_materia_prima.dto.response.*;
import com.api.nico.api_papas_nico.compra_materia_prima.model.*;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class CompraMateriaPrimaMapper {

    public CompraMateriaPrimaResponseDTO toResponseDTO(
            CabeceraCompraMateriaPrima c,
            List<DetalleCompraMateriaPrima> detalles
    ) {
        var r = new CompraMateriaPrimaResponseDTO();

        r.setId(c.getId());
        r.setFechaCompra(
                Fechas.mostrar(c.getFecha(), c.getHora(), c.getFechaCompra())
        );
        r.setHoraConocida(c.getHora() != null);
        r.setTotal(c.getTotal());
        r.setDetalles(detalles.stream().map(this::linea).toList());

        return r;
    }

    private DetalleCompraMPResponseDTO linea(DetalleCompraMateriaPrima d) {
        var r = new DetalleCompraMPResponseDTO();

        r.setId(d.getId());
        r.setMateriaPrimaId(d.getMateriaPrima().getId());
        r.setNombreMateriaPrima(d.getMateriaPrima().getNombre());
        r.setCantidad(d.getCantidad());
        r.setUnidad(d.getUnidad());
        r.setImportePagado(d.getImportePagado());
        r.setSubtotal(d.getImportePagado());

        return r;
    }
}
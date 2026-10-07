package com.api.nico.api_papas_nico.compra_comida.service.implementation;

import com.api.nico.api_papas_nico.common.Fechas;
import com.api.nico.api_papas_nico.compra_comida.dto.response.*;
import com.api.nico.api_papas_nico.compra_comida.model.*;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class CompraComidaMapper {

    public CompraComidaResponseDTO toResponseDTO(
            CabeceraCompraComida c,
            List<DetalleCompraComida> detalles
    ) {
        var r = new CompraComidaResponseDTO();

        r.setId(c.getId());
        r.setFechaCompra(
                Fechas.mostrar(c.getFecha(), c.getHora(), c.getFechaCompra())
        );
        r.setHoraConocida(c.getHora() != null);
        r.setTotal(c.getTotal());
        r.setDetalles(detalles.stream().map(this::linea).toList());

        return r;
    }

    private DetalleCompraResponseDTO linea(DetalleCompraComida d) {
        var r = new DetalleCompraResponseDTO();

        r.setId(d.getId());
        r.setComidaId(d.getComidaId().getId());
        r.setNombreComida(d.getComidaId().getNombre());
        r.setCantidad(d.getCantidad());
        r.setPrecioUnitario(d.getPrecioUnitario());

        r.setSubtotal(
                d.getPrecioUnitario() == null
                        ? null
                        : d.getPrecioUnitario().multiply(
                        BigDecimal.valueOf(d.getCantidad())
                )
        );

        return r;
    }
}
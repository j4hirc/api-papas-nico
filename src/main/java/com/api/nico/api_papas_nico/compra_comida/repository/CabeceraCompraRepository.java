package com.api.nico.api_papas_nico.compra_comida.repository;

import com.api.nico.api_papas_nico.compra_comida.model.CabeceraCompraComida;
import jakarta.persistence.LockModeType;
import java.time.LocalDate;
import java.util.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

public interface CabeceraCompraRepository
        extends JpaRepository<CabeceraCompraComida, Long> {

    List<CabeceraCompraComida> findByFechaBetweenOrderByFechaAscHoraAscIdAsc(
            LocalDate desde,
            LocalDate hasta
    );

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select c from cabecera_compra_comida c where c.id = :id")
    Optional<CabeceraCompraComida> bloquear(@Param("id") Long id);
}
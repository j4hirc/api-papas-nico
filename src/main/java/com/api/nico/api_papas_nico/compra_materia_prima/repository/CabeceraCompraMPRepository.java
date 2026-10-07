package com.api.nico.api_papas_nico.compra_materia_prima.repository;

import com.api.nico.api_papas_nico.compra_materia_prima.model.CabeceraCompraMateriaPrima;
import jakarta.persistence.LockModeType;
import java.time.LocalDate;
import java.util.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

public interface CabeceraCompraMPRepository
        extends JpaRepository<CabeceraCompraMateriaPrima, Long> {

    List<CabeceraCompraMateriaPrima> findByFechaBetweenOrderByFechaAscHoraAscIdAsc(
            LocalDate desde,
            LocalDate hasta
    );

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select c from cabecera_compra_materia_prima c where c.id = :id")
    Optional<CabeceraCompraMateriaPrima> bloquear(@Param("id") Long id);
}
package com.api.nico.api_papas_nico.comidas.repository;

import com.api.nico.api_papas_nico.comidas.model.Comidas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ComidaRepository extends JpaRepository<Comidas,Long> {
}

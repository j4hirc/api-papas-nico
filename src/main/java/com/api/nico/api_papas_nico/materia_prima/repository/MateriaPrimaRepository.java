package com.api.nico.api_papas_nico.materia_prima.repository;


import com.api.nico.api_papas_nico.materia_prima.model.MateriaPrima;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MateriaPrimaRepository extends JpaRepository<MateriaPrima, Long> {

}

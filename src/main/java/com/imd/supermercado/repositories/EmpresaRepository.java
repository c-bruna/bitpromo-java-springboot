package com.imd.supermercado.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.imd.supermercado.model.EmpresaEntity;

public interface EmpresaRepository extends JpaRepository<EmpresaEntity, Long> {

    boolean existsByCnpj(String cnpj);
    boolean existsByEmail(String email);
}
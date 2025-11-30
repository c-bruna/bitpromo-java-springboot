package com.imd.supermercado.repositories;

import com.imd.supermercado.model.ClienteEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<ClienteEntity, Long> {

    boolean existsByCpf(String cpf);

    boolean existsByEmail(String email);
    
    List<ClienteEntity> findAllByAtivoTrue();
}
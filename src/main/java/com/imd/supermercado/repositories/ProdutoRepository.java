package com.imd.supermercado.repositories;

import com.imd.supermercado.model.ProdutoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProdutoRepository extends JpaRepository<ProdutoEntity, Long> {
    List<ProdutoEntity> findAllByAtivoTrue();

    Optional<ProdutoEntity> findByIdAndAtivoTrue(Long id);
    Optional<ProdutoEntity> findByIdAndAtivoFalse(Long id);
}

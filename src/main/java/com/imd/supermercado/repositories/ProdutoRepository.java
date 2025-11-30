package com.imd.supermercado.repositories;

import com.imd.supermercado.model.ProdutoEntity;
import com.imd.supermercado.model.ProdutoEntity.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProdutoRepository extends JpaRepository<ProdutoEntity, Long> {

    List<ProdutoEntity> findAllByEmpresaId(Long empresaId);

    List<ProdutoEntity> findAllByCategoria(Categoria categoria);

    List<ProdutoEntity> findByNomeProdutoContainingIgnoreCase(String nomeProduto);
}
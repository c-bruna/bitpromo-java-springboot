package com.imd.supermercado.services;

import com.imd.supermercado.DTO.ProdutoDTO;
import com.imd.supermercado.model.ProdutoEntity;
import com.imd.supermercado.repositories.ProdutoRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProdutoService {
    @Autowired
    ProdutoRepository repository;

    public ProdutoEntity salvarProduto(ProdutoDTO produtoDTO) {
        ProdutoEntity produtoEntity = new ProdutoEntity();
        BeanUtils.copyProperties(produtoDTO, produtoEntity);
        return repository.save(produtoEntity);
    }

    public ProdutoEntity atualizarProduto(ProdutoDTO produtoDTO, Long id) {
        Optional<ProdutoEntity> produto = repository.findById(id);
        if (produto.isEmpty()) {
            return null;
        }
        ProdutoEntity produtoEntity = produto.get();
        produtoEntity.atualizarProduto(produtoDTO);
        return repository.save(produtoEntity);
    }

    public boolean apagarProduto(Long id) {
        Optional<ProdutoEntity> produto = repository.findById(id);
        if (produto.isEmpty()) {
            return false;
        }
        repository.delete(produto.get());
        return true;
    }

    public ProdutoEntity buscarProduto(Long id) {
        Optional<ProdutoEntity> produto = repository.findById(id);
        return produto.orElse(null);
    }

    public List<ProdutoEntity> buscarProdutosAtivos() {
        return repository.findAllByAtivoTrue();
    }

    public List<ProdutoEntity> buscarProdutos() {
        return repository.findAll();
    }

    public boolean ativarProduto(Long id) {
        Optional<ProdutoEntity> produto = repository.findById(id);
        if (produto.isEmpty()) {
            return false;
        }
        ProdutoEntity produtoEntity = produto.get();
        produtoEntity.ativar();
        repository.save(produtoEntity);
        return true;
    }

    public boolean desativarProduto(Long id) {
        Optional<ProdutoEntity> produto = repository.findById(id);
        if (produto.isEmpty()) {
            return false;
        }
        ProdutoEntity produtoEntity = produto.get();
        produtoEntity.desativar();
        repository.save(produtoEntity);
        return true;
    }

    public ProdutoRepository repository() {
        return repository;
    }
}


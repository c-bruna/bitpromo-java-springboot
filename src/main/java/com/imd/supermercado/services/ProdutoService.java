package com.imd.supermercado.services;

import com.imd.supermercado.DTO.ProdutoDTO;
import com.imd.supermercado.model.EmpresaEntity;
import com.imd.supermercado.model.ProdutoEntity;
import com.imd.supermercado.repositories.EmpresaRepository;
import com.imd.supermercado.repositories.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private EmpresaRepository empresaRepository;

    public ProdutoEntity salvarProduto(ProdutoDTO dto, Long empresaId) {

        EmpresaEntity empresa = empresaRepository.findById(empresaId)
                .orElseThrow(() -> new RuntimeException("Empresa não encontrada"));

        ProdutoEntity novoProduto = new ProdutoEntity(dto, empresa);

        return produtoRepository.save(novoProduto);
    }

    public ProdutoEntity buscarProduto(Long id) {
        return produtoRepository.findById(id).orElse(null);
    }

    public List<ProdutoEntity> buscarProdutos() {
        return produtoRepository.findAll();
    }

    public List<ProdutoEntity> buscarProdutosPorEmpresa(Long empresaId) {
        return produtoRepository.findAllByEmpresaId(empresaId);
    }

    public ProdutoEntity atualizarProduto(ProdutoDTO dto, Long id, Long empresaId) {

        ProdutoEntity produto = produtoRepository.findById(id).orElse(null);
        if (produto == null) {
            return null;
        }

        EmpresaEntity empresa = empresaRepository.findById(empresaId)
                .orElseThrow(() -> new RuntimeException("Empresa não encontrada"));

        produto.atualizarProduto(dto, empresa);

        return produtoRepository.save(produto);
    }

    public boolean apagarProduto(Long id) {
        if (!produtoRepository.existsById(id)) {
            return false;
        }
        produtoRepository.deleteById(id);
        return true;
    }
}
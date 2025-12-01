package com.imd.supermercado.services;

import com.imd.supermercado.DTO.ProdutoDTO;
import com.imd.supermercado.DTO.ProdutoVector;
import com.imd.supermercado.model.EmpresaEntity;
import com.imd.supermercado.model.ProdutoEntity;
import com.imd.supermercado.repositories.EmpresaRepository;
import com.imd.supermercado.repositories.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private EmpresaRepository empresaRepository;

    @Autowired
    private RestTemplate restTemplate;

    private final String PYTHON_API_URL = "http://localhost:8000/api/v1/insert/sincronizar";

    public ProdutoEntity salvarProduto(ProdutoDTO dto, Long empresaId) {

        EmpresaEntity empresa = empresaRepository.findById(empresaId)
                .orElseThrow(() -> new RuntimeException("Empresa não encontrada"));

        ProdutoEntity novoProduto = new ProdutoEntity(dto, empresa);

        ProdutoEntity produtoSalvo = produtoRepository.save(novoProduto);

        sincronizarComVectorDb(produtoSalvo);

        return produtoSalvo;
    }

    public ProdutoEntity atualizarProduto(ProdutoDTO dto, Long id, Long empresaId) {

        ProdutoEntity produto = produtoRepository.findById(id).orElse(null);
        if (produto == null) {
            return null;
        }

        EmpresaEntity empresa = empresaRepository.findById(empresaId)
                .orElseThrow(() -> new RuntimeException("Empresa não encontrada"));

        produto.atualizarProduto(dto, empresa);

        ProdutoEntity produtoAtualizado = produtoRepository.save(produto);

        sincronizarComVectorDb(produtoAtualizado);

        return produtoAtualizado;
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

    public boolean apagarProduto(Long id) {
        if (!produtoRepository.existsById(id)) {
            return false;
        }
        produtoRepository.deleteById(id);
        return true;
    }

    public List<ProdutoEntity> buscarProdutos(String nome, Double precoMin, Double precoMax) {
        if (nome == null) nome = "";
        if (precoMin == null) precoMin = 0.0;
        if (precoMax == null) precoMax = Double.MAX_VALUE;

        return produtoRepository.findByNomeProdutoContainingIgnoreCaseAndPrecoBetween(nome, precoMin, precoMax);
    }

    private void sincronizarComVectorDb(ProdutoEntity produto) {
        try {
            ProdutoVector vectorDto = new ProdutoVector(
                    produto.getId(),
                    produto.getNomeProduto(),
                    produto.getCategoria(),
                    produto.getPreco(),
                    produto.getImagem(),
                    produto.getLink()
            );

            restTemplate.postForEntity(PYTHON_API_URL, vectorDto, Void.class);
            System.out.println("Produto sincronizado com ChromaDB: " + produto.getNomeProduto());

        } catch (Exception e) {
            System.err.println("ERRO ao sincronizar com API Python: " + e.getMessage());
        }
    }
}
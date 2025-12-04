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
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import java.util.stream.Collectors;

import java.util.List;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private EmpresaRepository empresaRepository;

    @Autowired
    private RestTemplate restTemplate;

    public List<ProdutoEntity> buscarProdutosPorSemelhanca(String query) {
        try {
            String url = UriComponentsBuilder.fromHttpUrl("http://localhost:8000/api/v1/busca/buscar_semantica")
                    .queryParam("q", query)
                    .queryParam("limit", 10)
                    .queryParam("min_score", 0.3)
                    .toUriString();

            ResponseEntity<List<ProdutoVector>> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<ProdutoVector>>() {}
            );

            List<ProdutoVector> dtos = response.getBody();

            if (dtos == null || dtos.isEmpty()) {
                return List.of();
            }

            return dtos.stream().map(dto -> {
                ProdutoEntity p = new ProdutoEntity();
                p.setId(dto.getId());
                p.setNomeProduto(dto.getNomeProduto());
                p.setCategoria(dto.getCategoria());
                p.setPreco(dto.getPreco());
                p.setImagem(dto.getImagem());
                p.setLink(dto.getLink());
                return p;
            }).collect(Collectors.toList());

        } catch (Exception e) {
            System.err.println("Erro ao conectar na API Python: " + e.getMessage());
            return produtoRepository.findByNomeProdutoContainingIgnoreCase(query);
        }
    }

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

            restTemplate.postForEntity("http://localhost:8000/api/v1/insert/sincronizar", vectorDto, Void.class);
            System.out.println("Produto sincronizado com ChromaDB: " + produto.getNomeProduto());

        } catch (Exception e) {
            System.err.println("ERRO ao sincronizar com API Python: " + e.getMessage());
        }
    }
}
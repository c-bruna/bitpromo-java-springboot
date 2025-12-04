package com.imd.supermercado.DTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ProdutoVector {

    private Long id;
    private String nomeProduto;
    private String categoria;
    private Double preco;
    private String imagem;
    private String link;
    private Double scoreSimilaridade;
    public ProdutoVector() {}

    public ProdutoVector(Long id, String nomeProduto, String categoria, Double preco, String imagem, String link) {
        this.id = id;
        this.nomeProduto = nomeProduto;
        this.categoria = categoria;
        this.preco = preco;
        this.imagem = imagem;
        this.link = link;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomeProduto() {
        return nomeProduto;
    }

    public void setNomeProduto(String nomeProduto) {
        this.nomeProduto = nomeProduto;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public Double getPreco() {
        return preco;
    }

    public void setPreco(Double preco) {
        this.preco = preco;
    }

    public String getImagem() {
        return imagem;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
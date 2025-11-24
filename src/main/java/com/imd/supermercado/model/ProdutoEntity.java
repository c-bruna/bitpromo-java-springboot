package com.imd.supermercado.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.imd.supermercado.DTO.ProdutoDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Entity
@Table(name = "produtos")
public class ProdutoEntity {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
    String nomeProduto;
    String marca;
    LocalDate dataFabricacao;
    LocalDate dataValidade;
    String lote;
    Boolean ativo = true;
    @Enumerated(EnumType.STRING)
    Genero genero;

    @ManyToMany(mappedBy = "produtos", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JsonIgnore
    private List<PedidoEntity> pedidos;

    public ProdutoEntity() {

    }

    public ProdutoEntity(ProdutoDTO produtoDTO) {
        this.nomeProduto = produtoDTO.nomeProduto();
        this.marca = produtoDTO.marca();
        this.dataFabricacao = produtoDTO.dataFabricacao();
        this.dataValidade = produtoDTO.dataValidade();
        this.lote = produtoDTO.lote();
        this.genero = produtoDTO.genero();
    }

    public void atualizarProduto(ProdutoDTO produtoDTO) {
        if (produtoDTO.nomeProduto() != null) this.nomeProduto = produtoDTO.nomeProduto();
        if (produtoDTO.marca() != null) this.marca = produtoDTO.marca();
        if (produtoDTO.dataFabricacao() != null) this.dataFabricacao = produtoDTO.dataFabricacao();
        if (produtoDTO.dataValidade() != null) this.dataValidade = produtoDTO.dataValidade();
        if (produtoDTO.lote() != null) this.lote = produtoDTO.lote();
        if (produtoDTO.genero() != null) this.genero = produtoDTO.genero();
    }

    public void ativar(){ this.ativo = true; }

    public void desativar(){
        this.ativo = false;
    }

    public enum Genero {
        COSMETICO,
        ALIMENTICIO,
        HIGIENE_PESSOAL,
        LIMPEZA
    }

}



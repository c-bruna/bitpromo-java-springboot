package com.imd.supermercado.model;

import com.imd.supermercado.DTO.ProdutoDTO;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "produtos")
public class ProdutoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private Long id;

    @Column(nullable = false)
    private String nomeProduto;

    @Column(nullable = true)
    private Double preco;

    @Column(nullable = true)
    private String imagem;

    @Column(nullable = true, columnDefinition = "TEXT")
    private String link;

    @Column(nullable = false)
    private String categoria;

    @ManyToOne
    @JoinColumn(name = "empresa_id")
    private EmpresaEntity empresa;

    public ProdutoEntity(ProdutoDTO dto, EmpresaEntity empresa) {
        this.nomeProduto = dto.nomeProduto();
        this.preco = dto.preco();
        this.imagem = dto.imagem();
        this.link = dto.link();
        this.categoria = dto.categoria();
        this.empresa = empresa;
    }

    public void atualizarProduto(ProdutoDTO dto, EmpresaEntity empresa) {
        if (dto.nomeProduto() != null) this.nomeProduto = dto.nomeProduto();
        if (dto.preco() != null) this.preco = dto.preco();
        if (dto.imagem() != null) this.imagem = dto.imagem();
        if (dto.link() != null) this.link = dto.link();
        if (dto.categoria() != null) this.categoria = dto.categoria();
        if (empresa != null) this.empresa = empresa;
    }

    public enum Categoria {

        SMARTPHONES_ELETRONICOS_TV("SMARTPHONES, ELETRÔNICOS E TV"),
        INFORMATICA_GAMES("INFORMÁTICA E GAMES"),
        CASA_ELETRODOMESTICOS("CASA E ELETRODOMÉSTICOS"),
        MODA_ACESSORIOS("MODA E ACESSÓRIOS"),
        PRODUTOS_PARA_SEU_PET("PRODUTOS PARA SEU PET"),
        OUTROS("OUTROS");

        private final String label;

        Categoria(String label) {
            this.label = label;
        }

        public String getLabel() {
            return label;
        }

        public static Categoria fromLabel(String label) {
            for (Categoria c : values()) {
                if (c.label.equalsIgnoreCase(label)) {
                    return c;
                }
            }
            throw new IllegalArgumentException("Categoria inválida: " + label);
        }
    }

}
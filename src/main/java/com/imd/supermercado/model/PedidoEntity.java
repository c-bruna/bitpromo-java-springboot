package com.imd.supermercado.model;

import com.imd.supermercado.DTO.PedidoDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Optional;

@Getter
@Setter
@AllArgsConstructor
@Entity
@Table(name = "pedidos")
public class PedidoEntity {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
    @Column(nullable = false, unique = true)
    String codigo;
    Boolean ativo = true;

    @ManyToMany
    @JoinTable(
            name = "pedido_produtos",
            joinColumns = @JoinColumn(name = "pedido_id"),
            inverseJoinColumns = @JoinColumn(name = "produto_id")
    )
    List<ProdutoEntity> produtos;

    @Setter
    @ManyToOne
    @JoinColumn(name = "cliente_id")
    ClienteEntity cliente;


    public PedidoEntity() {

    }

    public PedidoEntity(PedidoDTO pedidoDTO, ClienteEntity cliente, List<ProdutoEntity> produtos) {
        this.codigo = pedidoDTO.codigo();
        this.cliente = cliente;
        this.produtos = produtos;
    }

    public void atualizarPedido(PedidoDTO pedidoDTO, ClienteEntity cliente, List<ProdutoEntity> produtos) {
        if (pedidoDTO.codigo() != null) {
            this.codigo = pedidoDTO.codigo();
        }
        if (cliente != null) {
            this.cliente = cliente;
        }
        if (produtos != null && !produtos.isEmpty()) {
            this.produtos = produtos;
        }
    }


    public void ativar(){ this.ativo = true; }

    public void desativar(){ this.ativo = false; }
}



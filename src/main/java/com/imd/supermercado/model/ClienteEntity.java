package com.imd.supermercado.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.imd.supermercado.DTO.ClienteDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Entity
@Table(name = "clientes")
public class ClienteEntity {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
    String nome;
    String cpf;
    @Enumerated(EnumType.STRING)
    Genero genero;
    Date dataNascimento;
    Boolean ativo = true;

    @JsonIgnore
    @OneToMany(mappedBy = "cliente")
    List<PedidoEntity> pedidos;

    public ClienteEntity() { }
    public ClienteEntity(ClienteDTO clienteDTO) {
        this.nome = clienteDTO.nome();
        this.cpf = clienteDTO.cpf();
        this.genero = clienteDTO.genero();
        this.dataNascimento = clienteDTO.dataNascimento();
    }

    public void atualizarCliente(ClienteDTO clienteDTO) {
        if (clienteDTO.nome() != null) this.nome = clienteDTO.nome();
        if (clienteDTO.cpf() != null) this.cpf = clienteDTO.cpf();
        if (clienteDTO.genero() != null) this.genero = clienteDTO.genero();
        if (clienteDTO.dataNascimento() != null) this.dataNascimento = clienteDTO.dataNascimento();
    }

    public void ativar(){
        this.ativo = true;
    }

    public void desativar(){
        this.ativo = false;
    }

    public enum Genero {
        FEMININO,
        MASCULINO,
        OUTRO
    }

}

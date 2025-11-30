package com.imd.supermercado.model;

import com.imd.supermercado.DTO.ClienteDTO;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "clientes")
public class ClienteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    @Column(unique = true, nullable = false)
    private String cpf;

    @Temporal(TemporalType.DATE)
    @Column(name = "data_nascimento", nullable = false)
    private Date dataNascimento;

    @Column(unique = true, nullable = false)
    private String email;

    private Boolean ativo = true;

    public ClienteEntity(ClienteDTO dto) {
        this.nome = dto.getNome();
        this.cpf = dto.getCpf();
        this.dataNascimento = dto.getDataNascimento();
        this.email = dto.getEmail();
        this.ativo = true;
    }

    public void atualizarCliente(ClienteDTO dto) {
        if (dto.getNome() != null) this.nome = dto.getNome();
        if (dto.getCpf() != null) this.cpf = dto.getCpf();
        if (dto.getDataNascimento() != null) this.dataNascimento = dto.getDataNascimento();
        if (dto.getEmail() != null) this.email = dto.getEmail();
    }

    public void ativar(){
        this.ativo = true;
    }

    public void desativar(){
        this.ativo = false;
    }
}

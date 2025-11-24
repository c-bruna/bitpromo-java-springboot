package com.imd.supermercado.DTO;

import com.imd.supermercado.model.ClienteEntity;
import org.hibernate.validator.constraints.br.CPF;
import jakarta.validation.constraints.NotNull;

import java.util.Date;

public record ClienteDTO(
        @NotNull
        String nome,

        @NotNull
        @CPF(message = "Digite um CPF válido")
        String cpf,

        @NotNull
        Date dataNascimento,

        @NotNull
        ClienteEntity.Genero genero) {
}

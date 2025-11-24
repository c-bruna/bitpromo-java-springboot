package com.imd.supermercado.DTO;

import com.imd.supermercado.model.ProdutoEntity;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record ProdutoDTO(
        @NotNull
        String nomeProduto,
        @NotNull
        String marca,
        @NotNull
        LocalDate dataFabricacao,
        LocalDate dataValidade,
        @NotNull
        String lote,
        @NotNull
        ProdutoEntity.Genero genero
) {}

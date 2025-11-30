package com.imd.supermercado.DTO;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Size;

public record ProdutoDTO(

        @NotNull(message = "O nome do produto é obrigatório")
        @Size(min = 2, max = 255)
        String nomeProduto,

        @DecimalMin(value = "0.0", message = "O preço não pode ser negativo")
        Double preco,

        String imagem,

        String link,

        @NotNull(message = "A categoria é obrigatória")
        String categoria,

        @NotNull(message = "A empresa do produto é obrigatória")
        Long empresaId
) {}
package com.imd.supermercado.DTO;

import jakarta.validation.constraints.NotNull;

import java.util.List;

public record PedidoDTO(
        @NotNull
        String codigo,
        Long idCliente,
        List<Long> idProdutos
){}

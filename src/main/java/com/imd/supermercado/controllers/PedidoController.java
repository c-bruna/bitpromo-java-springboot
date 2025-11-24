package com.imd.supermercado.controllers;

import com.imd.supermercado.DTO.PedidoDTO;
import com.imd.supermercado.model.PedidoEntity;
import com.imd.supermercado.services.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    @Autowired
    PedidoService pedidoService;

    // postPedido
    @PostMapping("/salvar")
    public ResponseEntity<PedidoEntity> salvarPedido(@RequestBody @Valid PedidoDTO pedidoDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(pedidoService.salvarPedido(pedidoDTO));
    }

    // getById
    @GetMapping("/{id}")
    public ResponseEntity<Object> buscarPedido(@PathVariable Long id) {
        PedidoEntity pedido = pedidoService.buscarPedido(id);
        if (pedido == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pedido não localizado");
        }
        return ResponseEntity.ok().body(pedido);
    }

    // getAll
    @GetMapping
    public ResponseEntity<List<PedidoEntity>> buscarTodosPedidos() {
        return ResponseEntity.ok().body(pedidoService.buscarPedidos());
    }

    @PutMapping("/adicionar/produtos/{pedidoId}")
    public ResponseEntity<Object> adicionarProdutos(@PathVariable Long pedidoId, @RequestBody List<Long> produtosIds) {
        var pedido = pedidoService.adicionarProdutos(pedidoId, produtosIds);
        if (pedido == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pedido ou Produtos não encontrados.");
        }
        return ResponseEntity.ok(pedido);
    }


    @PutMapping("/remover/produtos/{pedidoId}")
    public ResponseEntity<Object> removerProdutos(@PathVariable Long pedidoId, @RequestBody List<Long> produtosIds) {
        var pedido = pedidoService.removerProdutos(pedidoId, produtosIds);
        if (pedido == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pedido ou Produtos não encontrados.");
        }
        return ResponseEntity.ok(pedido);
    }


    @GetMapping("/ativos")
    public ResponseEntity<List<PedidoEntity>> buscarPedidosAtivos() {
        return ResponseEntity.ok().body(pedidoService.buscarPedidosAtivos());
    }

    // putPedido
    @PutMapping("/atualizar/{id}")
    public ResponseEntity<Object> atualizarPedido(@PathVariable Long id, @RequestBody PedidoDTO pedidoDTO) {
        PedidoEntity pedidoAtualizado = pedidoService.atualizarPedido(pedidoDTO, id);
        if (pedidoAtualizado == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pedido não localizado");
        }
        return ResponseEntity.ok().body(pedidoAtualizado);
    }

    // DeletePedido -
    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<Object> deletarPedido(@PathVariable Long id) {
        boolean resultado = pedidoService.apagarPedido(id);
        if (!resultado) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pedido não localizado");
        }
        return ResponseEntity.ok("Pedido deletado");
    }

    // DeleteLogic
    @PutMapping("/desativar/{id}")
    public ResponseEntity<Object> desativarPedido(@PathVariable Long id) {
        boolean resultado = pedidoService.desativarPedido(id);
        if (!resultado) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pedido não localizado");
        }
        return ResponseEntity.ok("Pedido desativado");
    }

    @PutMapping("/ativar/{id}")
    public ResponseEntity<Object> ativarPedido(@PathVariable Long id) {
        boolean resultado = pedidoService.ativarPedido(id);
        if (!resultado) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pedido não localizado");
        }
        return ResponseEntity.ok("Pedido ativado");
    }
}

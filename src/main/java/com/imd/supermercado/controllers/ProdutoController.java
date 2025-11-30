package com.imd.supermercado.controllers;

import com.imd.supermercado.DTO.ProdutoDTO;
import com.imd.supermercado.services.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/produto")
public class ProdutoController {

    @Autowired
    ProdutoService produtoService;

    @PostMapping("/salvar/{empresaId}")
    public ResponseEntity<?> salvarProduto(
            @RequestBody @Valid ProdutoDTO dto,
            @PathVariable Long empresaId
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(produtoService.salvarProduto(dto, empresaId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> buscarProduto(@PathVariable Long id) {
        var produto = produtoService.buscarProduto(id);
        if (produto == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Produto não localizado");
        }
        return ResponseEntity.ok(produto);
    }

    @GetMapping("/todos")
    public ResponseEntity<Object> buscarProdutos() {
        return ResponseEntity.ok(produtoService.buscarProdutos());
    }

    @GetMapping("/empresa/{empresaId}")
    public ResponseEntity<Object> buscarPorEmpresa(@PathVariable Long empresaId) {
        return ResponseEntity.ok(produtoService.buscarProdutosPorEmpresa(empresaId));
    }

    @PutMapping("/atualizar/{id}/{empresaId}")
    public ResponseEntity<?> atualizarProduto(
            @PathVariable Long id,
            @PathVariable Long empresaId,
            @RequestBody ProdutoDTO dto
    ) {
        var produto = produtoService.atualizarProduto(dto, id, empresaId);
        if (produto == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Produto não localizado");
        return ResponseEntity.ok(produto);
    }

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<Object> deletarProduto(@PathVariable Long id) {
        var resultado = produtoService.apagarProduto(id);
        if (!resultado) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Produto não localizado");
        }
        return ResponseEntity.ok("Produto deletado");
    }
}
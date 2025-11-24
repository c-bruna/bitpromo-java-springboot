package com.imd.supermercado.controllers;

import com.imd.supermercado.DTO.ProdutoDTO;
import com.imd.supermercado.model.ProdutoEntity;
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

    // postProduto
    @PostMapping("/salvar")
    public ResponseEntity<ProdutoEntity> salvarProduto(@RequestBody @Valid ProdutoDTO produtoDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(produtoService.salvarProduto(produtoDTO));
    }

    // getById
    @GetMapping("/{id}")
    public ResponseEntity<Object> buscarProduto(@PathVariable Long id) {
        var produto = produtoService.buscarProduto(id);
        if (produto == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Produto não localizado");
        }
        return ResponseEntity.ok().body(produto);
    }

    // getAll
    @GetMapping("/produtos")
    public ResponseEntity<Object> buscarProdutos() {
        return ResponseEntity.ok().body(produtoService.buscarProdutos());
    }

    @GetMapping("/produtos_ativos")
    public ResponseEntity<Object> buscarProdutosAtivos() {
        return ResponseEntity.ok().body(produtoService.buscarProdutosAtivos());
    }

    // putProduto
    @PutMapping("/atualizar/{id}")
    public ResponseEntity<Object> atualizarProduto(@PathVariable Long id, @RequestBody ProdutoDTO produtoDTO) {
        var produto = produtoService.atualizarProduto(produtoDTO, id);
        if (produto == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Produto não localizado");
        }
        return ResponseEntity.ok().body(produto);
    }

    // DeleteProduto
    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<Object> deletarProduto(@PathVariable Long id) {
        var resultado = produtoService.apagarProduto(id);
        if (!resultado) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Produto não localizado");
        }
        return ResponseEntity.ok("Produto deletado");
    }

    // DeleteLogic
    @PutMapping("/desativar/{id}")
    public ResponseEntity<Object> desativarProduto(@PathVariable Long id) {
        var resultado = produtoService.desativarProduto(id);
        if (!resultado) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Produto não localizado");
        }
        return ResponseEntity.ok("Produto desativado");
    }

    @PutMapping("/ativar/{id}")
    public ResponseEntity<Object> ativarProduto(@PathVariable Long id) {
        var resultado = produtoService.ativarProduto(id);
        if (!resultado) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Produto não localizado");
        }
        return ResponseEntity.ok("Produto ativado");
    }
}

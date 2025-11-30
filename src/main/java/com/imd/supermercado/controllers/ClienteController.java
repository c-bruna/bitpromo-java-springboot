package com.imd.supermercado.controllers;

import com.imd.supermercado.DTO.ClienteDTO;
import com.imd.supermercado.model.ClienteEntity;
import com.imd.supermercado.services.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/cliente")
public class ClienteController {

    @Autowired
    ClienteService clienteService;

    @PostMapping("/salvar")
    public ResponseEntity<ClienteEntity> salvarCliente(@RequestBody @Valid ClienteDTO clienteDTO){
        return ResponseEntity.status(HttpStatus.CREATED).body(clienteService.salvarCliente(clienteDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> buscarCliente(@PathVariable Long id){
        var cliente = clienteService.buscarCliente(id);
        if(cliente == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cliente não localizado");
        }
        return ResponseEntity.ok().body(cliente);
    }

    @GetMapping("/clientes")
    public ResponseEntity<Object> buscarClientes(){ return ResponseEntity.ok().body(clienteService.buscarClientes()); }

    @GetMapping("/clientes_ativos")
    public ResponseEntity<Object> buscarClientesAtivos(){ return ResponseEntity.ok().body(clienteService.buscarClientesAtivos()); }

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<Object> atualizarCliente(@PathVariable Long id, @RequestBody ClienteDTO clienteDTO) {
        var cliente = clienteService.atualizarCliente(clienteDTO, id);
        if (cliente == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cliente não localizado");
        }

        return ResponseEntity.ok().body(cliente);
    }

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<Object> deletarCliente(@PathVariable Long id) {
        var cliente = clienteService.ApagarCliente(id);
        if (cliente == true) {
            return ResponseEntity.ok("Cliente deletado");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cliente não localizado");
    }

    @PutMapping("/desativar/{id}")
    public ResponseEntity<Object> desativarCliente(@PathVariable Long id) {
        var cliente = clienteService.desativarCliente(id);
        if (cliente == false) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cliente não localizado");
        }
        return ResponseEntity.ok("Cliente desativado");
    }

    @PutMapping("/ativar/{id}")
    public ResponseEntity<Object> ativarCliente(@PathVariable Long id) {
        var cliente = clienteService.ativarCliente(id);
        if (cliente == false) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cliente não localizado");
        }
        return ResponseEntity.ok("Cliente ativado");
    }

}
package com.imd.supermercado.controllers;

import org.springframework.web.bind.annotation.*;

import com.imd.supermercado.DTO.EmpresaDTO;
import com.imd.supermercado.model.EmpresaEntity;
import com.imd.supermercado.services.EmpresaService;

import java.util.List;

@RestController
@RequestMapping("/empresa")
public class EmpresaController {

    private final EmpresaService empresaService;

    public EmpresaController(EmpresaService empresaService) {
        this.empresaService = empresaService;
    }

    @PostMapping("/salvar")
    public EmpresaEntity criar(@RequestBody EmpresaDTO dto) {
        return empresaService.salvarEmpresa(dto);
    }

    @GetMapping("/listar")
    public List<EmpresaEntity> listar() {
        return empresaService.listarEmpresas();
    }

    @GetMapping("/{id}")
    public EmpresaEntity buscarPorId(@PathVariable Long id) {
        return empresaService.buscarPorId(id);
    }

    @PutMapping("/atualizar/{id}")
    public EmpresaEntity atualizar(@PathVariable Long id, @RequestBody EmpresaDTO dto) {
        return empresaService.atualizarEmpresa(id, dto);
    }

    @DeleteMapping("/deletar/{id}")
    public String deletar(@PathVariable Long id) {
        boolean ok = empresaService.deletarEmpresa(id);
        if (ok) return "Empresa deletada com sucesso!";
        return "Empresa não encontrada.";
    }
}
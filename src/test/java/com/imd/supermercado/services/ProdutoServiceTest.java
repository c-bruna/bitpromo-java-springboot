// java
package com.imd.supermercado.services;

import com.imd.supermercado.DTO.ProdutoDTO;
import com.imd.supermercado.model.EmpresaEntity;
import com.imd.supermercado.model.ProdutoEntity;
import com.imd.supermercado.repositories.EmpresaRepository;
import com.imd.supermercado.repositories.ProdutoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProdutoServiceTest {

    @Mock
    ProdutoRepository produtoRepository;

    @Mock
    EmpresaRepository empresaRepository;

    @InjectMocks
    ProdutoService produtoService;

    @Test
    void salvarProduto_deve_salvar_quando_empresa_existir() {
        ProdutoDTO dto = new ProdutoDTO("Produto X", 10.0, "img.png", "http://link", "Categoria", 1L);
        EmpresaEntity empresa = new EmpresaEntity();
        ProdutoEntity saved = new ProdutoEntity();

        when(empresaRepository.findById(1L)).thenReturn(Optional.of(empresa));
        when(produtoRepository.save(any())).thenReturn(saved);

        ProdutoEntity result = produtoService.salvarProduto(dto, 1L);

        assertSame(saved, result);
        verify(produtoRepository, times(1)).save(any(ProdutoEntity.class));
    }

    @Test
    void salvarProduto_deve_lancar_quando_empresa_nao_existir() {
        when(empresaRepository.findById(2L)).thenReturn(Optional.empty());
        ProdutoDTO dto = new ProdutoDTO("Produto Y", 5.0, null, null, "Categoria", 2L);
        assertThrows(RuntimeException.class, () -> produtoService.salvarProduto(dto, 2L));
    }

    @Test
    void buscarProduto_deve_retornar_quando_existir() {
        ProdutoEntity p = new ProdutoEntity();
        when(produtoRepository.findById(3L)).thenReturn(Optional.of(p));

        ProdutoEntity result = produtoService.buscarProduto(3L);

        assertSame(p, result);
    }

    @Test
    void buscarProduto_deve_retornar_null_quando_nao_existir() {
        when(produtoRepository.findById(4L)).thenReturn(Optional.empty());

        ProdutoEntity result = produtoService.buscarProduto(4L);

        assertNull(result);
    }

    @Test
    void buscarProdutos_deve_retornar_todos() {
        List<ProdutoEntity> list = Arrays.asList(new ProdutoEntity(), new ProdutoEntity());
        when(produtoRepository.findAll()).thenReturn(list);

        List<ProdutoEntity> result = produtoService.buscarProdutos();

        assertSame(list, result);
    }

    @Test
    void buscarProdutosPorEmpresa_deve_delegar() {
        List<ProdutoEntity> list = List.of(new ProdutoEntity());
        when(produtoRepository.findAllByEmpresaId(10L)).thenReturn(list);

        List<ProdutoEntity> result = produtoService.buscarProdutosPorEmpresa(10L);

        assertSame(list, result);
    }

    @Test
    void atualizarProduto_deve_retornar_null_quando_produto_nao_existir() {
        when(produtoRepository.findById(5L)).thenReturn(Optional.empty());
        ProdutoDTO dto = new ProdutoDTO("Atualiza", 2.0, null, null, "Categoria", 1L);

        ProdutoEntity result = produtoService.atualizarProduto(dto, 5L, 1L);

        assertNull(result);
    }

    @Test
    void atualizarProduto_deve_lancar_quando_empresa_nao_existir() {
        ProdutoEntity existing = new ProdutoEntity();
        when(produtoRepository.findById(6L)).thenReturn(Optional.of(existing));
        when(empresaRepository.findById(99L)).thenReturn(Optional.empty());

        ProdutoDTO dto = new ProdutoDTO("Atualiza", 3.0, null, null, "Categoria", 99L);
        assertThrows(RuntimeException.class, () -> produtoService.atualizarProduto(dto, 6L, 99L));
    }

    @Test
    void apagarProduto_deve_deletar_se_existir() {
        when(produtoRepository.existsById(7L)).thenReturn(true);

        boolean result = produtoService.apagarProduto(7L);

        assertTrue(result);
        verify(produtoRepository, times(1)).deleteById(7L);
    }

    @Test
    void apagarProduto_deve_retornar_false_se_nao_existir() {
        when(produtoRepository.existsById(8L)).thenReturn(false);

        boolean result = produtoService.apagarProduto(8L);

        assertFalse(result);
        verify(produtoRepository, never()).deleteById(any());
    }

    @Test
    void buscarProdutos_com_filtros_nulos_deve_usar_padroes() {
        List<ProdutoEntity> list = List.of(new ProdutoEntity());
        when(produtoRepository.findByNomeProdutoContainingIgnoreCaseAndPrecoBetween("", 0.0, Double.MAX_VALUE))
                .thenReturn(list);

        List<ProdutoEntity> result = produtoService.buscarProdutos(null, null, null);

        assertSame(list, result);
    }
}

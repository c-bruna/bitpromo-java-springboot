// java
package com.imd.supermercado.services;

import com.imd.supermercado.DTO.EmpresaDTO;
import com.imd.supermercado.model.EmpresaEntity;
import com.imd.supermercado.model.UserEntity;
import com.imd.supermercado.repositories.EmpresaRepository;
import com.imd.supermercado.repositories.UserRepository;
import com.imd.supermercado.security.RoleEnum;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class EmpresaServiceTest {

    @MockBean
    EmpresaRepository empresaRepository;

    @MockBean
    UserRepository userRepository;

    @MockBean
    PasswordEncoder passwordEncoder;

    @Autowired
    EmpresaService empresaService;

    @Test
    void salvarEmpresa_deve_salvar_empresa_e_criar_usuario() {
        EmpresaDTO dto = new EmpresaDTO();
        dto.setNome("Acme");
        dto.setCnpj("123");
        dto.setEmail("acme@example.com");
        dto.setSenha("senha");

        EmpresaEntity saved = new EmpresaEntity();
        when(empresaRepository.save(any())).thenReturn(saved);
        when(passwordEncoder.encode("senha")).thenReturn("encoded");

        EmpresaEntity result = empresaService.salvarEmpresa(dto);

        assertSame(saved, result);
        ArgumentCaptor<UserEntity> captor = ArgumentCaptor.forClass(UserEntity.class);
        verify(userRepository, times(1)).save(captor.capture());
        UserEntity user = captor.getValue();
        assertEquals("acme@example.com", user.getLogin());
        assertEquals("encoded", user.getPassword());
        assertEquals(RoleEnum.EMPRESA, user.getRole());
        assertNotNull(user.getEmpresa());
    }

    @Test
    void listarEmpresas_deve_retornar_lista() {
        List<EmpresaEntity> list = Arrays.asList(new EmpresaEntity());
        when(empresaRepository.findAll()).thenReturn(list);

        List<EmpresaEntity> result = empresaService.listarEmpresas();

        assertSame(list, result);
    }

    @Test
    void buscarPorId_deve_retornar_quando_existir() {
        EmpresaEntity e = new EmpresaEntity();
        when(empresaRepository.findById(1L)).thenReturn(Optional.of(e));

        EmpresaEntity result = empresaService.buscarPorId(1L);

        assertSame(e, result);
    }

    @Test
    void buscarPorId_deve_lancar_quando_nao_existir() {
        assertThrows(RuntimeException.class, () -> empresaService.buscarPorId(2L));
    }

    @Test
    void atualizarEmpresa_deve_atualizar_campos() {
        EmpresaEntity existing = new EmpresaEntity();
        existing.setNome("Old");
        when(empresaRepository.findById(3L)).thenReturn(Optional.of(existing));
        when(empresaRepository.save(existing)).thenReturn(existing);

        EmpresaDTO dto = new EmpresaDTO();
        dto.setNome("New");

        EmpresaEntity result = empresaService.atualizarEmpresa(3L, dto);

        assertSame(existing, result);
        assertEquals("New", existing.getNome());
    }

    @Test
    void deletarEmpresa_deve_deletar_quando_existir() {
        EmpresaEntity existing = new EmpresaEntity();
        when(empresaRepository.findById(4L)).thenReturn(Optional.of(existing));

        boolean result = empresaService.deletarEmpresa(4L);

        assertTrue(result);
        verify(empresaRepository, times(1)).delete(existing);
    }

    @Test
    void deletarEmpresa_deve_retornar_false_quando_nao_existir() {
        boolean result = empresaService.deletarEmpresa(5L);

        assertFalse(result);
        verify(empresaRepository, never()).delete(any());
    }
}

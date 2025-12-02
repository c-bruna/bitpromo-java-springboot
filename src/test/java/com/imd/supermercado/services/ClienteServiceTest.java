// java
package com.imd.supermercado.services;

import com.imd.supermercado.DTO.ClienteDTO;
import com.imd.supermercado.model.ClienteEntity;
import com.imd.supermercado.model.UserEntity;
import com.imd.supermercado.repositories.ClienteRepository;
import com.imd.supermercado.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ClienteServiceTest {

    @Mock
    ClienteRepository repository;

    @Mock
    UserRepository userRepository;

    @Mock
    PasswordEncoder passwordEncoder;

    @InjectMocks
    ClienteService service;

    @Test
    void salvarCliente_deve_salvar_cliente_e_usuario() {
        ClienteDTO dto = new ClienteDTO();
        dto.setEmail("teste@example.com");
        dto.setSenha("plain");

        ClienteEntity savedCliente = mock(ClienteEntity.class);
        when(repository.save(any(ClienteEntity.class))).thenReturn(savedCliente);
        when(passwordEncoder.encode("plain")).thenReturn("encoded");

        ClienteEntity result = service.salvarCliente(dto);

        assertSame(savedCliente, result);
        verify(repository, times(1)).save(any(ClienteEntity.class));
        verify(passwordEncoder, times(1)).encode("plain");

        ArgumentCaptor<UserEntity> userCaptor = ArgumentCaptor.forClass(UserEntity.class);
        verify(userRepository, times(1)).save(userCaptor.capture());
        UserEntity savedUser = userCaptor.getValue();
        assertEquals("teste@example.com", savedUser.getLogin());
        assertEquals("encoded", savedUser.getPassword());
        assertNotNull(savedUser.getCliente());
    }

    @Test
    void atualizarCliente_deve_atualizar_quando_existir() {
        Long id = 1L;
        ClienteDTO dto = new ClienteDTO();
        dto.setEmail("novo@example.com");

        ClienteEntity existing = mock(ClienteEntity.class);
        ClienteEntity updated = mock(ClienteEntity.class);

        when(repository.findById(id)).thenReturn(Optional.of(existing));
        when(repository.save(existing)).thenReturn(updated);

        ClienteEntity result = service.atualizarCliente(dto, id);

        assertSame(updated, result);
        verify(repository, times(1)).findById(id);
        verify(repository, times(1)).save(existing);
    }

    @Test
    void atualizarCliente_deve_retornar_null_quando_nao_existir() {
        Long id = 2L;
        when(repository.findById(id)).thenReturn(Optional.empty());

        ClienteEntity result = service.atualizarCliente(new ClienteDTO(), id);

        assertNull(result);
        verify(repository, times(1)).findById(id);
        verify(repository, never()).save(any());
    }

    @Test
    void ApagarCliente_deve_deletar_quando_existir() {
        Long id = 3L;
        ClienteEntity existing = mock(ClienteEntity.class);
        when(repository.findById(id)).thenReturn(Optional.of(existing));

        boolean result = service.ApagarCliente(id);

        assertTrue(result);
        verify(repository, times(1)).delete(existing);
    }

    @Test
    void ApagarCliente_deve_retornar_false_quando_nao_existir() {
        Long id = 4L;
        when(repository.findById(id)).thenReturn(Optional.empty());

        boolean result = service.ApagarCliente(id);

        assertFalse(result);
        verify(repository, never()).delete(any());
    }

    @Test
    void buscarCliente_deve_retornar_quando_existir() {
        Long id = 5L;
        ClienteEntity existing = mock(ClienteEntity.class);
        when(repository.findById(id)).thenReturn(Optional.of(existing));

        ClienteEntity result = service.buscarCliente(id);

        assertSame(existing, result);
    }

    @Test
    void buscarCliente_deve_retornar_null_quando_nao_existir() {
        Long id = 6L;
        when(repository.findById(id)).thenReturn(Optional.empty());

        ClienteEntity result = service.buscarCliente(id);

        assertNull(result);
    }

    @Test
    void buscarClientesAtivos_deve_retornar_lista() {
        List<ClienteEntity> lista = Arrays.asList(mock(ClienteEntity.class));
        when(repository.findAllByAtivoTrue()).thenReturn(lista);

        List<ClienteEntity> result = service.buscarClientesAtivos();

        assertSame(lista, result);
    }

    @Test
    void buscarClientes_deve_retornar_todos() {
        List<ClienteEntity> lista = Arrays.asList(mock(ClienteEntity.class), mock(ClienteEntity.class));
        when(repository.findAll()).thenReturn(lista);

        List<ClienteEntity> result = service.buscarClientes();

        assertSame(lista, result);
    }

    @Test
    void ativarCliente_deve_ativar_quando_existir() {
        Long id = 7L;
        ClienteEntity existing = mock(ClienteEntity.class);
        when(repository.findById(id)).thenReturn(Optional.of(existing));

        boolean result = service.ativarCliente(id);

        assertTrue(result);
        verify(existing, times(1)).ativar();
        verify(repository, times(1)).save(existing);
    }

    @Test
    void ativarCliente_deve_retornar_false_quando_nao_existir() {
        Long id = 8L;
        when(repository.findById(id)).thenReturn(Optional.empty());

        boolean result = service.ativarCliente(id);

        assertFalse(result);
        verify(repository, never()).save(any());
    }

    @Test
    void desativarCliente_deve_desativar_quando_existir() {
        Long id = 9L;
        ClienteEntity existing = mock(ClienteEntity.class);
        when(repository.findById(id)).thenReturn(Optional.of(existing));

        boolean result = service.desativarCliente(id);

        assertTrue(result);
        verify(existing, times(1)).desativar();
        verify(repository, times(1)).save(existing);
    }

    @Test
    void desativarCliente_deve_retornar_false_quando_nao_existir() {
        Long id = 10L;
        when(repository.findById(id)).thenReturn(Optional.empty());

        boolean result = service.desativarCliente(id);

        assertFalse(result);
        verify(repository, never()).save(any());
    }

    @Test
    void repository_deve_retornar_o_repositorio_injetado() {
        assertSame(repository, service.repository());
    }
}
package com.imd.supermercado.services;

import com.imd.supermercado.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
public class AuthorizationServiceTest {
    @Mock
    UserRepository repository;
    @InjectMocks
    AuthorizationService service;

    @Test
    void loadUserByUsername_deve_retornar_UserDetails_quando_existir() {
        UserDetails mockUser = mock(UserDetails.class);
        when(repository.findByLogin("alice")).thenReturn(mockUser);

        UserDetails result = service.loadUserByUsername("alice");

        assertSame(mockUser, result);
        verify(repository, times(1)).findByLogin("alice");
    }


    @Test
    void loadUserByUsername_deve_retornar_null_quando_nao_existir() {
        when(repository.findByLogin("missing")).thenReturn(null);

        assertNull(service.loadUserByUsername("missing"));
    }
}

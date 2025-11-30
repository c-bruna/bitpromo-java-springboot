package com.imd.supermercado.DTO;

import com.imd.supermercado.security.RoleEnum;

public record RegistrarDTO(
        String login,
        String password,
        RoleEnum role) {
}
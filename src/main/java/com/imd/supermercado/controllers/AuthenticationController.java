package com.imd.supermercado.controllers;

import com.imd.supermercado.DTO.LoginDTO;
import com.imd.supermercado.DTO.LoginResponseDTO;
import com.imd.supermercado.DTO.RegistrarDTO;
import com.imd.supermercado.model.UserEntity;
import com.imd.supermercado.repositories.UserRepository;
import com.imd.supermercado.services.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository repository;

    @Autowired
    TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginDTO loginDTO){
        var userPassword = new UsernamePasswordAuthenticationToken(loginDTO.login(), loginDTO.password());
        var auth = this.authenticationManager.authenticate(userPassword);

        var token = tokenService.generateToken((UserEntity)auth.getPrincipal());

        return ResponseEntity.ok(new LoginResponseDTO(token));
    }

    @PostMapping("/registrar")
    public ResponseEntity registra(@RequestBody RegistrarDTO registrarDTO){
        if(this.repository.findByLogin(registrarDTO.login()) != null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        String encriptedPassword = new BCryptPasswordEncoder().encode(registrarDTO.password());
        UserEntity user = new UserEntity(registrarDTO.login(), encriptedPassword ,registrarDTO.role());
        this.repository.save(user);
        return ResponseEntity.ok().build();
    }
}

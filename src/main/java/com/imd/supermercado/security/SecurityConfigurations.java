package com.imd.supermercado.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfigurations {

    @Autowired
    SecurityFilter securityFilter;
     @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll()
                )

                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
    // @Bean
    // SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
    //     return httpSecurity
    //             .csrf(csrf -> csrf.disable())
    //             .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
    //             .authorizeHttpRequests(autorize -> autorize
    //                     .requestMatchers(HttpMethod.POST, "/auth/login").permitAll()
    //                     .requestMatchers(HttpMethod.POST, "/auth/registrar").permitAll()
    //                     .requestMatchers(HttpMethod.POST , "/cliente/salvar").hasRole("ADMIN")
    //                     .requestMatchers(HttpMethod.PUT , "/cliente/atualizar/").hasRole("ADMIN")
    //                     .requestMatchers(HttpMethod.DELETE , "/cliente/deletar/").hasRole("ADMIN")
    //                     .requestMatchers(HttpMethod.PUT , "/cliente/desativar/").hasRole("ADMIN")
    //                     .requestMatchers(HttpMethod.PUT , "/cliente/ativar/").hasRole("ADMIN")
    //                     .requestMatchers(HttpMethod.GET, "/cliente").hasAnyRole("ADMIN", "USER")
    //                     .requestMatchers(HttpMethod.GET, "/cliente/clientes").hasAnyRole("ADMIN", "USER")
    //                     .requestMatchers(HttpMethod.GET, "/cliente/clientes_ativos").hasAnyRole("ADMIN", "USER")
    //                     .requestMatchers(HttpMethod.POST, "/produto/salvar").hasRole("ADMIN")
    //                     .requestMatchers(HttpMethod.PUT, "/produto/atualizar").hasRole("ADMIN")
    //                     .requestMatchers(HttpMethod.DELETE, "/produto/deletar").hasRole("ADMIN")
    //                     .requestMatchers(HttpMethod.PUT, "/produto/desativar").hasRole("ADMIN")
    //                     .requestMatchers(HttpMethod.PUT, "/produto/ativar").hasRole("ADMIN")
    //                     .requestMatchers(HttpMethod.GET, "/produto").hasAnyRole("ADMIN", "USER")
    //                     .requestMatchers(HttpMethod.GET, "/produto/produtos").hasAnyRole("ADMIN", "USER")
    //                     .requestMatchers(HttpMethod.GET, "/produto/produtos_ativos").hasAnyRole("ADMIN", "USER")
    //                     .requestMatchers(HttpMethod.POST, "/pedidos/salvar").hasRole("ADMIN")
    //                     .requestMatchers(HttpMethod.PUT, "/pedidos/atualizar").hasRole("ADMIN")
    //                     .requestMatchers(HttpMethod.DELETE, "/pedidos/deletar").hasRole("ADMIN")
    //                     .requestMatchers(HttpMethod.PUT, "/pedidos/desativar").hasRole("ADMIN")
    //                     .requestMatchers(HttpMethod.PUT, "/pedidos/ativar").hasRole("ADMIN")
    //                     .requestMatchers(HttpMethod.PUT, "/pedidos/adicionar/produtos/").hasRole("ADMIN")
    //                     .requestMatchers(HttpMethod.PUT, "/pedidos/remover/produtos/").hasRole("ADMIN")
    //                     .requestMatchers(HttpMethod.GET, "/pedidos").hasAnyRole("ADMIN", "USER")
    //                     .requestMatchers(HttpMethod.GET, "/pedidos/ativos").hasAnyRole("ADMIN", "USER")
    //                     .anyRequest().authenticated()
    //             )
    //             .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
    //             .build();
    // }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}

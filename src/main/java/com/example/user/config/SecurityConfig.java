package com.example.user.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/public/**").permitAll() // Rutas públicas sin autenticación
                .anyRequest().authenticated() // Todas las demás rutas requieren autenticación
                .and()
                .formLogin() // Habilitar el formulario de inicio de sesión (puedes personalizarlo según tus necesidades)
                .permitAll(); // Permitir el acceso al formulario de inicio de sesión sin autenticación
    }
}

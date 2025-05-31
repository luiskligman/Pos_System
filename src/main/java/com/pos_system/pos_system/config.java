package com.pos_system.pos_system;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.context.annotation.Bean;

@Configuration
public class config {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/h2-console/**").permitAll() // allow H2 access
                        .anyRequest().permitAll()) // or restrict as needed
                .csrf(csrf -> csrf.disable()) // required for H2
                .headers(headers -> headers.frameOptions().disable()); // also for H2

        return http.build();
    }
}
package com.xdwolf.airlineticket.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable) // Tắt CSRF cho Postman
                .authorizeHttpRequests(auth -> auth
                                // Cho phép truy cập tự do các static resource
                                .requestMatchers(
                                        "/css/**", "/js/**", "/images/**", "/fonts/**"
                                ).permitAll()

                                // Cho phép các trang public
                                .requestMatchers(
                                        "/", "/index", "/about", "/services",
                                        "/contact", "/booking-management",
                                        "/auth/login", "/auth/register"
                                ).permitAll()

                                // Cho phép các API public
                                .requestMatchers(
                                        "/api/user/register", "/api/user/login"
                                ).permitAll()

                                .requestMatchers(
                                        "/api/airport/**", "/api/flight/**", "/api/booking/**"
                                ).permitAll()

                                //  Các request khác yêu cầu login
                                .anyRequest().authenticated()
                        )
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .logout(AbstractHttpConfigurer::disable);
        return http.build();
    }
}

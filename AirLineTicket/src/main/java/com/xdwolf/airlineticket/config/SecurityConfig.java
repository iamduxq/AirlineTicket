package com.xdwolf.airlineticket.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomUserDetailsService userDetailsService;
    private final CustomAuthSuccessHandler successHandler;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

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
                                .requestMatchers("/api/flight/**", "/api/airport/**").permitAll()

                                // API yêu cầu login
                                .requestMatchers("/api/booking/**").authenticated()
                                .requestMatchers("/admin/**").hasRole("ADMIN")

                                //  Các request khác yêu cầu login
                                .anyRequest().authenticated()
                        )
                .formLogin(login -> login
                        .loginPage("/auth/login")
                        .loginProcessingUrl("/auth/do-login")
                        .successHandler(successHandler)
                        .failureUrl("/auth/login?error=true")
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/auth/logout")
                        .logoutSuccessUrl("/")
                        .permitAll()
                )
                .rememberMe(remember -> remember
                        .rememberMeParameter("remember-me")
                        .tokenValiditySeconds(60 * 60 * 24 * 7)
                        .key("xdwolf-secret-key-123")
                )
                .httpBasic(AbstractHttpConfigurer::disable);
        return http.build();
    }
}

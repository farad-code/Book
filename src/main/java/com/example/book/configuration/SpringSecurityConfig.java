package com.example.book.configuration;

import com.example.book.constant.Constant;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.cors.reactive.CorsWebFilter;

import java.util.Arrays;
import java.util.List;

import static org.springframework.security.config.Customizer.withDefaults;

@EnableWebSecurity
@Configuration
public class SpringSecurityConfig {

    private final AuthenticationFilter jwtAuthenticationFilter = new AuthenticationFilter();
    private final ApplicationConfig applicationConfig = new ApplicationConfig();

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        AuthenticationProvider authenticationProvider = applicationConfig.authenticationProvider();
        httpSecurity
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers("/api/auth/**")
                        .permitAll()
                        .requestMatchers("/api/user/**").hasAuthority(Constant.ROLE_ADMIN)
                        .requestMatchers(HttpMethod.DELETE,Constant.BOOK_URL).hasAuthority(Constant.ROLE_ADMIN)
                        .requestMatchers(HttpMethod.PUT,Constant.BOOK_URL).hasAuthority(Constant.ROLE_ADMIN)
                        .requestMatchers(HttpMethod.POST,Constant.BOOK_URL).hasAuthority(Constant.ROLE_ADMIN)

                        .anyRequest()
                        .authenticated()
                )
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return httpSecurity.build();
    }



}

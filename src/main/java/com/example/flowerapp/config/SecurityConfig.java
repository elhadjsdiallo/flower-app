package com.example.flowerapp.config;

import java.util.Arrays;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

        private static final String[] WHITE_LISTS_PATH = { "/rest/api/public/**" };
        private final JwtAuthFilter jwtAuthFilter;

  

        @Bean
        SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
                http
                                .csrf(csrf->csrf.disable())
                                .cors(Customizer.withDefaults())
                                .authorizeHttpRequests(authCustomizer -> authCustomizer
                                .requestMatchers(WHITE_LISTS_PATH).permitAll()
                                .anyRequest()
                                .authenticated()
                                )               
                        .oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt)
                        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                        // .exceptionHandling((ex)->ex.authenticationEntryPoint(new BearerTokenAuthenticationEntryPoint())
                        // .accessDeniedHandler(new BearerTokenAccessDeniedHandler()))
                        .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
                       return  http.build();
        
        }

        @Bean
        CorsConfigurationSource corsConfigurationSource() {
            CorsConfiguration configuration = new CorsConfiguration();
            configuration.setAllowedOrigins(Arrays.asList("*"));
            configuration.setAllowedMethods(Arrays.asList("*"));
            configuration.setAllowedHeaders(List.of("*"));
            UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
            source.registerCorsConfiguration("/**", configuration);
            return source;
        }

}

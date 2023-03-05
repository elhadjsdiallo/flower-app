package com.example.flowerapp.config;

import java.io.IOException;

import javax.security.sasl.AuthenticationException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.security.oauth2.server.resource.authentication.BearerTokenAuthenticationToken;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.flowerapp.exception.ServiceBaseException;
import com.example.flowerapp.response.CustomApiResponse;
import com.example.flowerapp.services.JwtTokenService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Component
@RequiredArgsConstructor
@Log4j2
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtTokenService jwtTokenService;

    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        String token;
        if (authHeader != null && Boolean.TRUE.equals(authHeader.startsWith("Bearer "))) {
            token = authHeader.substring("Bearer ".length(), authHeader.length());

            if (Boolean.TRUE.equals(jwtTokenService.isProvideToken(token))) {
                String userName = jwtTokenService.extractUserName(token);

                if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    UserDetails userDetails = userDetailsService.loadUserByUsername(userName);

                    try {
                        if (jwtTokenService.isTokenValid(token, userDetails)) {

                            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                                    userDetails,
                                    null,
                                    userDetails.getAuthorities());
                            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                            SecurityContext context = SecurityContextHolder.createEmptyContext();
                            context.setAuthentication(authenticationToken);
                            SecurityContextHolder.setContext(context);

                        }
                    } catch (ServiceBaseException e) {
                        log.error("Failed to validate token");
                        e.printStackTrace();
                    }

                }
            }

        }
        filterChain.doFilter(request, response);

    }

}

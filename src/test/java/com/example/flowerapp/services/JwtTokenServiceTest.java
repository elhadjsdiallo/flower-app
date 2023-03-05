package com.example.flowerapp.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.test.context.support.WithMockUser;

import com.example.flowerapp.exception.ServiceBaseException;

@SpringBootTest
@WithMockUser("user1")
class JwtTokenServiceTest {

    @Autowired
    private JwtTokenService jwtTokenService;
    @Autowired
    private JwtDecoder jwtDecoder;

    private Authentication authentication;

    @BeforeEach
    void setup() {
        authentication = SecurityContextHolder.getContext()
                .getAuthentication();

    }

    @Test
    @DisplayName("Test the generation of the token based on userDetails or authentication")

    void testOfJwtTokenGeneration() {
        // GIVEN authentication

        // WHEN
        String token = jwtTokenService.generateToken(authentication);

        String subject = jwtDecoder.decode(token).getSubject();
        // THEN
        assertNotNull(token);
        assertEquals("user1", subject);

    }

    @Test
    @DisplayName("Test the validy of token including expiration")
    void testifTokenIsValid() throws ServiceBaseException {

        // GIVEN
        String token = jwtTokenService.generateToken(authentication);

        // WHEN
        Boolean isValid = jwtTokenService.isTokenValid(token, (UserDetails) authentication.getPrincipal());
        Exception exception = assertThrows(ServiceBaseException.class, () -> {
            jwtTokenService.isTokenValid("wrongtokoken", (UserDetails) authentication.getDetails());
        });

        assertTrue(isValid);
        assertTrue(exception.getMessage().contains("failed to decoded token"));
        // THEN

    }
}

package com.example.flowerapp.services;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import com.example.flowerapp.exception.ServiceBaseException;
import com.nimbusds.jose.proc.BadJOSEException;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Service
@Data
@RequiredArgsConstructor
public class JwtTokenService {

    private final JwtDecoder jwtDecoder;

    private final JwtEncoder jwtEncoder;

    public Boolean isProvideToken(String token) {
        try {
            
            jwtDecoder.decode(token);
            return true;
        } catch (Exception e) {

            return false;
        }
    }

    public String extractUserName(String token) {

        return jwtDecoder.decode(token).getSubject();

    }

    public Map<String, Object> getClaimAsMap(String token) {
        return jwtDecoder.decode(token).getClaims();

    }

    public Object getClaimOnToke(String token, String claim) {

        return getJwtDecoder().decode(token).getClaim(claim);

    }

    public boolean isTokenValid(String token, UserDetails userDetails) throws ServiceBaseException {
        
        try {
            String userName = extractUserName(token);
            return (userName.equalsIgnoreCase(userDetails.getUsername()) && isUserNotExpired(token));
            
        } catch (Exception e) {
            
            throw new ServiceBaseException("failed to decoded token");
        }

      
   

    }

    private boolean isUserNotExpired(String token) {

        Instant tokenExpirationDate = jwtDecoder.decode(token).getExpiresAt();
        if (tokenExpirationDate != null) {
            return tokenExpirationDate.isAfter(Instant.now());
        }

        return false;

    }

    public String generateToken(UserDetails userDetails) {

        Instant now = Instant.now();

        String scope = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(" "));

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(now)
                .expiresAt(now.plus(3, ChronoUnit.HOURS))
                .subject(userDetails.getUsername())
                .claim("scope", scope)
                .build();

        return getJwtEncoder().encode(JwtEncoderParameters.from(claims)).getTokenValue();

    }

    public String generateToken(Authentication authentication) {


        Instant now = Instant.now();
        String scope = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(" "));
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(now)
                .expiresAt(now.plus(1, ChronoUnit.HOURS))
                .subject(authentication.getName())
                .claim("scope", scope)
                .build();
        return getJwtEncoder().encode(JwtEncoderParameters.from(claims)).getTokenValue();


    }

}

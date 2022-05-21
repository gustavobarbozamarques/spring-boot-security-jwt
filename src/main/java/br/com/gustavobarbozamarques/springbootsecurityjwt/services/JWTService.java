package br.com.gustavobarbozamarques.springbootsecurityjwt.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.stream.Collectors;

@Service
public class JWTService {

    @Value("${jwt.expiration.minutes}")
    private Integer expirationMinutes;

    @Value("${jwt.secret}")
    private String secret;

    public String generateJwtToken(Authentication authentication) {
        Date expiration = Date.from(
                LocalDateTime.now()
                        .plusMinutes(expirationMinutes)
                        .atZone(ZoneId.systemDefault())
                        .toInstant()
        );

        var roles = authentication.getAuthorities()
                .stream()
                .map(grantedAuthority -> grantedAuthority.getAuthority())
                .collect(Collectors.toList());

        return JWT.create()
                .withSubject(authentication.getName())
                .withExpiresAt(expiration)
                .withClaim("roles", roles)
                .sign(Algorithm.HMAC256(secret));
    }

    public Authentication extractAuthentication(String jwtToken) {
        Algorithm algorithm = Algorithm.HMAC256(secret);
        JWTVerifier verifier = JWT.require(algorithm).build();
        DecodedJWT decodedJWT = verifier.verify(jwtToken);

        var name = decodedJWT.getSubject();
        var authorities = decodedJWT.getClaim("roles")
                .asList(String.class)
                .stream()
                .map(role -> new SimpleGrantedAuthority(role))
                .collect(Collectors.toList());
        return new UsernamePasswordAuthenticationToken(name, null, authorities);
    }
}

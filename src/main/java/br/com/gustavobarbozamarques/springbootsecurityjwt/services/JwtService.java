package br.com.gustavobarbozamarques.springbootsecurityjwt.services;

import br.com.gustavobarbozamarques.springbootsecurityjwt.enums.TokenTypeEnum;
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
public class JwtService {

    @Value("${jwt.access.token.expiration.minutes}")
    private Integer jwtAccessTokenExpirationMinutes;

    @Value("${jwt.access.token.secret}")
    private String jwtAccessTokenSecret;

    @Value("${jwt.refresh.token.expiration.minutes}")
    private Integer jwtRefreshTokenExpirationMinutes;

    @Value("${jwt.refresh.token.secret}")
    private String jwtRefreshTokenSecret;

    public String generateJwtToken(Authentication authentication, TokenTypeEnum tokenType) {
        Date expiration = Date.from(
                LocalDateTime.now()
                        .plusMinutes(tokenType == TokenTypeEnum.ACCESS_TOKEN ? jwtAccessTokenExpirationMinutes : jwtRefreshTokenExpirationMinutes)
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
                .sign(Algorithm.HMAC256(tokenType == TokenTypeEnum.ACCESS_TOKEN ? jwtAccessTokenSecret : jwtRefreshTokenSecret));
    }

    public Authentication extractAuthentication(String jwtToken, TokenTypeEnum tokenType) {
        Algorithm algorithm = Algorithm.HMAC256(tokenType == TokenTypeEnum.ACCESS_TOKEN ? jwtAccessTokenSecret : jwtRefreshTokenSecret);
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

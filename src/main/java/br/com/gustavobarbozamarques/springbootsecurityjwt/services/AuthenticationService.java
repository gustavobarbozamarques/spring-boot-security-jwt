package br.com.gustavobarbozamarques.springbootsecurityjwt.services;

import br.com.gustavobarbozamarques.springbootsecurityjwt.dto.LoginRequestDTO;
import br.com.gustavobarbozamarques.springbootsecurityjwt.dto.LoginResponseDTO;
import br.com.gustavobarbozamarques.springbootsecurityjwt.dto.RefreshTokenResponseDTO;
import br.com.gustavobarbozamarques.springbootsecurityjwt.enums.TokenTypeEnum;
import br.com.gustavobarbozamarques.springbootsecurityjwt.utils.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@Slf4j
public class AuthenticationService {

    @Autowired
    private AuthenticationManagerBuilder authenticationManagerBuilder;

    @Autowired
    private JwtService jwtService;

    public LoginResponseDTO login(LoginRequestDTO loginRequestDTO) {
        var credentials = new UsernamePasswordAuthenticationToken(loginRequestDTO.getUsername(), loginRequestDTO.getPassword());
        var authentication = authenticationManagerBuilder.getObject().authenticate(credentials);
        var accessToken = jwtService.generateJwtToken(authentication, TokenTypeEnum.ACCESS_TOKEN);
        var refreshToken = jwtService.generateJwtToken(authentication, TokenTypeEnum.REFRESH_TOKEN);
        return LoginResponseDTO.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    public RefreshTokenResponseDTO refreshToken(String authorizationHeader) {
        var jwtToken = JwtUtils.extractJwt(authorizationHeader);
        if (jwtToken == null) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        String newAccessToken;
        try {
            var authentication = jwtService.extractAuthentication(jwtToken, TokenTypeEnum.REFRESH_TOKEN);
            newAccessToken = jwtService.generateJwtToken(authentication, TokenTypeEnum.ACCESS_TOKEN);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        return RefreshTokenResponseDTO.builder()
                .newAccessToken(newAccessToken)
                .build();
    }
}

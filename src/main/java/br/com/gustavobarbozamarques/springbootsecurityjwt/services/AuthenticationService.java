package br.com.gustavobarbozamarques.springbootsecurityjwt.services;

import br.com.gustavobarbozamarques.springbootsecurityjwt.dto.LoginRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class AuthenticationService {

    @Autowired
    AuthenticationManagerBuilder authenticationManagerBuilder;

    public String login(LoginRequestDTO loginRequestDTO) {
        var credentials = new UsernamePasswordAuthenticationToken(loginRequestDTO.getUsername(), loginRequestDTO.getPassword());
        var authentication = authenticationManagerBuilder.getObject().authenticate(credentials);
        System.out.println("Authenticated: " + authentication.isAuthenticated());
        System.out.println("Name: " + authentication.getName());
        System.out.println("Roles: " + authentication.getAuthorities().stream().map(a -> a.getAuthority()).collect(Collectors.joining(",")));
        var jwtToken = "";
        return jwtToken;
    }
}

package br.com.gustavobarbozamarques.springbootsecurityjwt.controllers;

import br.com.gustavobarbozamarques.springbootsecurityjwt.dto.LoginRequestDTO;
import br.com.gustavobarbozamarques.springbootsecurityjwt.dto.LoginResponseDTO;
import br.com.gustavobarbozamarques.springbootsecurityjwt.dto.RefreshTokenResponseDTO;
import br.com.gustavobarbozamarques.springbootsecurityjwt.services.AuthenticationService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/public/auth")
public class AuthController {

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/login")
    @ApiOperation("Perform login and get a access token")
    public LoginResponseDTO login(@Valid @RequestBody LoginRequestDTO loginRequestDTO) {
        return authenticationService.login(loginRequestDTO);
    }

    @GetMapping("/refresh-token")
    @ApiOperation("Get a new access token")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Bearer abc...", required = true, allowEmptyValue = false, paramType = "header", dataTypeClass = String.class)
    })
    public RefreshTokenResponseDTO refreshToken(HttpServletRequest request) {
        return authenticationService.refreshToken(request.getHeader("Authorization"));
    }
}

package br.com.gustavobarbozamarques.springbootsecurityjwt.controllers;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.security.Principal;

@RestController
@RequestMapping("/api/private")
public class PrivateController {

    @GetMapping("/user")
    @ApiOperation("Only authenticated users can access")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Bearer abc...", required = true, allowEmptyValue = false, paramType = "header", dataTypeClass = String.class)
    })
    public String userResource(@ApiIgnore Principal principal) {
        return "Welcome user, %s".formatted(principal.getName());
    }

    @GetMapping("/admin")
    @PreAuthorize("hasAuthority('ADMIN')")
    @ApiOperation("Only authenticated users with ADMIN role can access")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Bearer abc...", required = true, allowEmptyValue = false, paramType = "header", dataTypeClass = String.class)
    })
    public String adminResource(@ApiIgnore Principal principal) {
        return "Welcome admin, %s".formatted(principal.getName());
    }
}

package br.com.gustavobarbozamarques.springbootsecurityjwt.filters;

import br.com.gustavobarbozamarques.springbootsecurityjwt.enums.TokenTypeEnum;
import br.com.gustavobarbozamarques.springbootsecurityjwt.services.JwtService;
import br.com.gustavobarbozamarques.springbootsecurityjwt.utils.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            var jwtToken = JwtUtils.extractJwt(request.getHeader("Authorization"));
            if (jwtToken != null) {
                var authentication = jwtService.extractAuthentication(jwtToken, TokenTypeEnum.ACCESS_TOKEN);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        filterChain.doFilter(request, response);
    }
}

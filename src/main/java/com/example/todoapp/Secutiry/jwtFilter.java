package com.example.todoapp.Secutiry;

import com.example.todoapp.User.DTO.DecodedTokenDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.catalina.filters.ExpiresFilter;
import org.apache.tomcat.util.http.parser.Authorization;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;


import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

public class jwtFilter extends GenericFilterBean {

    @Override
    public void doFilter(ServletRequest servletRequest
            , ServletResponse servletResponse
            , FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String authorization = request.getHeader("Authorization");
        if (authorization != null && authorization.startsWith("Bearer ")){
            String token = authorization.replace("Bearer ", "");

            DecodedTokenDTO jws = TokenAuthenticationService.getAuthentication(token);

            SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(
                    jws,
                    token,
                    Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"))
            ));
        }

        filterChain.doFilter(request, (HttpServletResponse)servletResponse);

    }
}

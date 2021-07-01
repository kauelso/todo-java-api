package com.example.todoapp.Secutiry;

import java.util.Base64;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.example.todoapp.User.DTO.DecodedTokenDTO;
import com.example.todoapp.User.Models.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import io.jsonwebtoken.Jwts;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public class TokenAuthenticationService {

    static final byte[] SECRET = Base64.getDecoder().decode("3lJ4FrACryWvs/H69VS+A6ZCTZqVCrXbl5IqyBmdfe0=");

    public TokenAuthenticationService(){}

    public static String addAuthentication(User user) {
        String jwt = Jwts.builder()
                .claim("username",user.getUsername())
                .claim("id",user.getId())
                .claim("isRemote",user.isRemoteDB())
                .claim("roles",user.isAdmin())
                .signWith(Keys.hmacShaKeyFor(SECRET))
                .compact();

        return jwt;
    }

    public static DecodedTokenDTO getAuthentication(String token) {
        if (token != null) {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(Keys.hmacShaKeyFor(SECRET))
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            DecodedTokenDTO user = new DecodedTokenDTO();
            user.setId(claims.get("id",String.class));
            user.setUsername(claims.get("username",String.class));
            user.setRemote(claims.get("isRemote",Boolean.class));
            user.setAdmin(claims.get("roles", Boolean.class));
            return user;
        }
        return null;
    }

}
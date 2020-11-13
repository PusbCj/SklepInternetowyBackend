package com.example.sklepinternetowy.security.jwt;

import com.google.common.base.Strings;
import com.google.common.net.HttpHeaders;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class JwtTokenVerificationToken extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {

        var authorizationHeader = httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION);

        if(Strings.isNullOrEmpty(authorizationHeader) || !authorizationHeader.startsWith("Bearer ")){
            filterChain.doFilter(httpServletRequest,httpServletResponse);
            return;
        }

        try {
            Jws<Claims> claimsJws = getClaimsJws(authorizationHeader);

            Authentication authentication = getAuthentication(claimsJws);

            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        catch (JwtException ex){
            throw new IllegalStateException("Token cannot be trusted");
        }

        filterChain.doFilter(httpServletRequest,httpServletResponse);
    }




    private Jws<Claims> getClaimsJws(String authorizationHeader) {
        String jwtToken = authorizationHeader.replace("Bearer ", "");

        Jws<Claims> claimsJws = Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor("secretkeyisverystrongbecauseitislong".getBytes()))
                .build().parseClaimsJws(jwtToken);
        return claimsJws;
    }





    private Authentication getAuthentication(Jws<Claims> claimsJws) {
        Claims body = claimsJws.getBody();
        String username = body.getSubject();
        List<String> authorities =  (List<String>) body.get("authorities");


        Set<SimpleGrantedAuthority> simpleGrantedAuthorities = authorities.stream()
                .map(x -> new SimpleGrantedAuthority(x))
                .collect(Collectors.toSet());

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                username,
                null,
                simpleGrantedAuthorities
        );
        return authentication;
    }
}

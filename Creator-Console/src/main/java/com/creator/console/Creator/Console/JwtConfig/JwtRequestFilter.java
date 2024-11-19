package com.creator.console.Creator.Console.JwtConfig;


import com.creator.console.Creator.Console.JwtConfig.JwtUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private MyUserDetailsService myUserDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // Get the JWT token from the Authorization header
        String authorizationHeader = request.getHeader("Authorization");

        String email = null;
        String jwtToken = null;

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            // Extract token from the header
            jwtToken = authorizationHeader.substring(7);
            email = jwtUtil.extractUsername(jwtToken);
        }

        if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            // If the user is not authenticated and the token is valid
            if (jwtUtil.validateToken(jwtToken, myUserDetailsService.loadUserByUsername(email))) {
               
                var userDetails = myUserDetailsService.loadUserByUsername(email);

                // Create an authentication object based on JWT token
                WebAuthenticationDetailsSource webAuthenticationDetailsSource = new WebAuthenticationDetailsSource();
                org.springframework.security.authentication.UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                        new org.springframework.security.authentication.UsernamePasswordAuthenticationToken(userDetails, null,
                                userDetails.getAuthorities());
                usernamePasswordAuthenticationToken.setDetails(webAuthenticationDetailsSource.buildDetails(request));

                // Set the authentication context
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }

        // Proceed with the next filter in the chain
        filterChain.doFilter(request, response);
    }

   
   
}

package com.blogapis.blogapi.security;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtTokenHelper jwtTokenHelper;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String username = null;
        String token = null;

        // get token
        String requestToken = request.getHeader("Authorization");
        log.warn("Token :: {}", requestToken);

        if (requestToken != null && requestToken.startsWith("Bearer")) {
            token = requestToken.substring(7);

            try {
                username = jwtTokenHelper.getUsernameFromToken(token);
            } catch (IllegalArgumentException ex) {
                log.error("Unable to get JWT token :: {}", ex);
            } catch (ExpiredJwtException ex) {
                log.error("JWT token has expired :: {}", ex);
            } catch (MalformedJwtException ex) {
                log.error("Invalid token :: {}", ex);
            }

        } else {
            log.error("Jwt token does start with Bearer :: {}", requestToken);
        }

        // token and username has been taken out
        // now validating the token

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            // now token has to be set in security context holder

            UserDetails userDetails =
                    userDetailsService.loadUserByUsername(username);

            if (jwtTokenHelper.validateToken(token, userDetails)) {
                // token is valid if true then token is validated
                // now authentication has to be done

                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                        new UsernamePasswordAuthenticationToken(
                                token, // principal
                                null, // credentials
                                userDetails.getAuthorities() // authorities
                        );

                usernamePasswordAuthenticationToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );

                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

            } else {
                // if token validation fails
                log.error("Invalid token");
            }

        } else {
            log.error("Username is null.");
        }

        filterChain.doFilter(request, response);

    }
}
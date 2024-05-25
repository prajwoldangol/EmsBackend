package com.prajwol.config;

import com.prajwol.exception.EmsCustomException;
import com.prajwol.userservice.EmployeeDetailsService;
import com.prajwol.userservice.EmployerDetailsService;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@Log4j2
public class JwtAuthFilter extends OncePerRequestFilter {

    private JwtTokenProvider tokenProvider;
    private EmployerDetailsService employerDetailsService;
    private EmployeeDetailsService employeeDetailsService;

    @Autowired
    public JwtAuthFilter(JwtTokenProvider tokenProvider, EmployerDetailsService employerDetailsService, EmployeeDetailsService employeeDetailsService) {
        this.tokenProvider = tokenProvider;
        this.employerDetailsService = employerDetailsService;
        this.employeeDetailsService = employeeDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String authorizationHeader = request.getHeader("Authorization");
            String username = null;
            String jwt = null;

            if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
                jwt = authorizationHeader.substring(7);
                username = tokenProvider.extractUsername(jwt);
            } else {
                log.info("Authorization header not found or invalid format.");
            }
            log.info("Username extracted from JWT token: {}", username);
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                String requestURI = request.getRequestURI();
                UserDetails userDetails = null;
                if (requestURI.startsWith("/employer/")) {
                    try{
                        userDetails = employerDetailsService.loadUserByUsername(username);
                    }catch(Exception e){
                        throw new EmsCustomException("Unauthorized Access", "401");
                    }

                } else {
                    userDetails = employeeDetailsService.loadUserByUsername(username);
                }
                if (tokenProvider.validateToken(jwt, userDetails.getUsername())) {
                    log.info("JWT token is valid.");
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                } else {
                    throw new EmsCustomException("Unauthorized Access", "401");
//                    log.info("JWT token is invalid or expired.");
                }
            } else {
                log.info("Username is null or authentication already exists in SecurityContextHolder.");
            }

            filterChain.doFilter(request, response);
        } catch (EmsCustomException e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            String errorMessage = "{\"error\": \"" + e.getErrorCode() + "\", \"message\": \"" + e.getMessage() + "\"}";
            response.getWriter().write(errorMessage);
            response.getWriter().flush();
        } catch (Exception e) {
            int statusCode = HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
            if (e instanceof ExpiredJwtException) {
                statusCode = HttpServletResponse.SC_UNAUTHORIZED;
            }
            log.error("An error occurred: {}", e.getMessage());
            response.setContentType("application/json");
            String errorMessage = "{\"error\": \"" + statusCode + "\", \"message\": \"" + e.getMessage() + "\"}";
            response.getWriter().write(errorMessage);

            response.getWriter().flush();
        }

    }
}

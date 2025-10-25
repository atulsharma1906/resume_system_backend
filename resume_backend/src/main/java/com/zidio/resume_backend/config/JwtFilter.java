package com.zidio.resume_backend.config;

import com.zidio.resume_backend.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class JwtFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(JwtFilter.class);

    private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService;

    public JwtFilter(JwtUtil jwtUtil, UserDetailsService userDetailsService) {
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req,
                                    HttpServletResponse resp,
                                    FilterChain chain)
            throws ServletException, IOException {

        String path = req.getServletPath();
        // Avoid logging sensitive token values; log only path at debug level
        logger.debug("Incoming path: {}", path);

        // Skip JWT check for login/register
        if (path.startsWith("/api/auth/")) {
            chain.doFilter(req, resp);
            return;
        }

        // Only attempt to set authentication if not already set
        if (SecurityContextHolder.getContext().getAuthentication() == null) {
            final String header = req.getHeader("Authorization");
            if (header != null && header.startsWith("Bearer ")) {
                String token = header.substring(7);
                try {
                    if (jwtUtil.validateToken(token)) {
                        String email = jwtUtil.extractUsername(token);

                        try {
                            UserDetails userDetails = userDetailsService.loadUserByUsername(email);
                            UsernamePasswordAuthenticationToken auth =
                                    new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                            SecurityContextHolder.getContext().setAuthentication(auth);
                        } catch (Exception e) {
                            // If user cannot be loaded (deleted/disabled), fallback to token-based authentication
                            logger.debug("UserDetails not found for {}: {}; falling back to token claims", email, e.getMessage());

                            String role = jwtUtil.extractRole(token);
                            List<SimpleGrantedAuthority> authorities = new ArrayList<>();
                            if (role != null && !role.isEmpty()) {
                                String normalized = role.startsWith("ROLE_") ? role : ("ROLE_" + role);
                                authorities.add(new SimpleGrantedAuthority(normalized));
                            }
                            UsernamePasswordAuthenticationToken auth =
                                    new UsernamePasswordAuthenticationToken(email, null, authorities);
                            SecurityContextHolder.getContext().setAuthentication(auth);
                        }

                    } else {
                        logger.debug("JWT validation failed for request to {}", path);
                    }
                } catch (Exception e) {
                    // Don't expose token or sensitive details in logs
                    logger.debug("Error validating JWT for request to {}: {}", path, e.getMessage());
                }
            }
        }

        chain.doFilter(req, resp);
    }
    
}
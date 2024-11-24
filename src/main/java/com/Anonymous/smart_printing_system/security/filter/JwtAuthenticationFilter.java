package com.Anonymous.smart_printing_system.security.filter;


import com.Anonymous.smart_printing_system.security.service.SystemUserDetailsService;
import com.Anonymous.smart_printing_system.security.util.JwtUtil;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter
        extends OncePerRequestFilter
{
    private final SystemUserDetailsService systemUserDetailsService;


    private final JwtUtil jwtUtil;


    @Value("{com.sps.serverName}")
    private String serverName;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException
    {
        try
        {
            final String authorizationHeader = request.getHeader("Authorization");
            if (authorizationHeader != null && authorizationHeader.startsWith("Bearer "))
            {
                String token = authorizationHeader.substring(7);
                if (jwtUtil.validateToken(token))
                {
                    String subject = jwtUtil.extractAllClaims(token).getSubject();

                    /*
                        Choose to load from the database for updated information
                        Instead, set the Spring Security Context for [Role based access control]
                     */
                    UserDetails userDetails = systemUserDetailsService.loadUserByUsername(subject);

                    /*
                        [Authentication object creation]
                     */
                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                            new UsernamePasswordAuthenticationToken(
                                    userDetails,
                                    null,
                                    userDetails.getAuthorities());
                    usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    /*
                        Set the context for [Authorization]
                     */
                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                }
            }
        }
        /* Immediately stop the filter chain */
        catch (UnsupportedJwtException e)
        {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            response.getWriter().write("{\"" + serverName + ":JWT_error_Unsupported-Exception" + "\": \"" + "Token is not supported" + "\"}");
            return;
        }
        catch (MalformedJwtException e)
        {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.setContentType("application/json");
            response.getWriter().write("{\"" + serverName + ":JWT_error_Malformed-Exception" + "\": \"" + "Token is malformed" + "\"}");
            return;
        }
        catch (SignatureException e)
        {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            response.getWriter().write("{\"" + serverName + ":JWT_error_Signature-Exception" + "\": \"" + "Signature is not matched" + "\"}");
            return;
        }
        /*
         * Catch all JwtException
         */
        catch (JwtException e)
        {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            response.getWriter().write("{\"" + serverName + ":JWT_error_Unknown-Exception" + "\": \"Token is invalid. Login redirect.\"}");
            return;
        }
        filterChain.doFilter(request, response);
    }
}

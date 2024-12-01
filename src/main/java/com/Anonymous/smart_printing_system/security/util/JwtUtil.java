package com.Anonymous.smart_printing_system.security.util;


import com.Anonymous.smart_printing_system.model.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;


@Component
public class JwtUtil
{
    @Value("${com.sps.secretKey}")
    private String secretKey;


    @Value("${com.sps.expiration}")
    private Long expiration;


    @Value("${com.sps.issuer}")
    private String issuer;


    @Value("${com.sps.audience}")
    private String audience;


    private SecretKey key;


    @PostConstruct
    public void init()
    {
        key = Keys.hmacShaKeyFor(secretKey.getBytes());
    }


    public String generateToken(Long id, String email, Set<Role> roles)
    {
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", id);
        claims.put("roles", roles);
        return createToken(claims, email);
    }


    public String createToken(Map<String, Object> claims, String email)
    {
        Date now = new Date(System.currentTimeMillis());
        Date exp = new Date(now.getTime() + expiration * 1000);

        return Jwts
                .builder()
                .claims(claims)
                .issuer(issuer)
                .subject(email)
                .audience()
                .add(audience)
                .and()
                .expiration(exp)
                .notBefore(now)
                .issuedAt(now)
                .signWith(key)
                .compact();
    }


    public Claims extractAllClaims(String token)
    {
        return Jwts
                .parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }


    public boolean isIssuerValid(String token)
    {
        return issuer
                .equals(extractAllClaims(token).getIssuer());
    }


    public boolean isSubjectValid(String token)
    {
        return true;
    }


    public boolean isAudienceValid(String token)
    {
        return true;
    }


    public boolean isNotExpired(String token)
    {
        return extractAllClaims(token)
                .getExpiration()
                .after(new Date());
    }


    public boolean isNotBeforeValid(String token)
    {
        return true;
    }


    public boolean validateToken(String token)
    {
        if (!isIssuerValid(token))
        {
            throw new JwtException("Issuer is invalid");
        }
        if (!isSubjectValid(token))
        {
            throw new JwtException("Subject is invalid");
        }
        if (!isAudienceValid(token))
        {
            throw new JwtException("Audience is invalid");
        }
        if (!isNotExpired(token))
        {
            throw new JwtException("JWT token is expired");
        }
        if (!isNotBeforeValid(token))
        {
            throw new JwtException("Token is not valid yet.");
        }
        return true;
    }
}

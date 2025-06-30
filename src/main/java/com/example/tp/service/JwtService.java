package com.example.tp.service;

import com.example.tp.modelo.Administrador;
import com.example.tp.modelo.Usuario;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Map;

@Service
public class JwtService {
    @Value("${application.security.jwt.secret-key}")
    private String secretKey;
    @Value("${application.security.jwt.expiration}")
    private long jwtExpiration;
    @Value("${application.security.jwt.refresh-token.expiration}")
    private long refreshExpiration;

    public String generateToken(final Usuario usuario) {
        return buildToken(usuario,refreshExpiration);
    }

    public String generateToken(final Administrador admin) {
        return buildToken(admin,refreshExpiration);
    }

    public String generateRefreshToken(final Usuario usuario) {
        return buildToken(usuario, refreshExpiration);
    }

    public String generateRefreshToken(final Administrador admin) {
        return buildToken(admin, refreshExpiration);
    }

    public String buildToken(final Usuario usuario, final long expiration) {
        Integer n=usuario.getId();

        return Jwts.builder()
                .id(n.toString())
                .claims(Map.of("name",usuario.getNombre()))
                .subject(usuario.getDni())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSingInKey())
                .compact();


    }

    public String buildToken(final Administrador admin, final long expiration) {
        Integer n=admin.getId();

        return Jwts.builder()
                .id(n.toString())
                .claims(Map.of("name",admin.getNombre()))
                .subject(admin.getDni())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSingInKey())
                .compact();


    }

    private SecretKey getSingInKey() {
        byte[] keyByter = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyByter);
    }

    public String extractUsername(final String token) {
        final Claims jwtToken = Jwts.parser()
                .verifyWith(getSingInKey())
                .build()
                .parseClaimsJws(token)
                .getPayload();
        return  jwtToken.getSubject();
    }

    public boolean isTokenValid(final String token, final Usuario usuario) {
        final String username = extractUsername(token);
        return(username.equals(usuario.getDni()) && !isTokenExpired(token));
    }

    private Date extractExpiration(final String token) {
        final Claims jwtToken = Jwts.parser()
                .verifyWith(getSingInKey())
                .build()
                .parseClaimsJws(token)
                .getPayload();
        return jwtToken.getExpiration();
    }

    private boolean isTokenExpired(final String token) {
        return extractExpiration(token).before(new Date());
    }
}

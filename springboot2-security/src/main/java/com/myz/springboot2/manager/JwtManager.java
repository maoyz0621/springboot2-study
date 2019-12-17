package com.myz.springboot2.manager;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;

import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * @author maoyz
 */
public class JwtManager {

    public static String generateToken(String username) {
        Key key = Keys.secretKeyFor(SignatureAlgorithm.HS512);
        String token = Jwts.builder().setSubject(username).setExpiration(expirate(2)).signWith(key, SignatureAlgorithm.HS512).compact();
        return token;
    }

    public static String getUsernameFromToken(String token) {
        Key key = Keys.secretKeyFor(SignatureAlgorithm.HS512);
        Jwt<Header, Claims> jwt = Jwts.parser()
                .setSigningKey(key)
                .parseClaimsJwt(token);
        String sub = jwt.getBody().getSubject();
        return sub;
    }

    private static Date expirate(long hours) {
        LocalDateTime localDateTime = LocalDateTime.now().plusHours(hours);
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    public static Boolean validateToken(String authToken, UserDetails userDetails) {
        return true;
    }
}

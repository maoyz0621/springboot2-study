package com.myz.springboot2.manager;

import com.myz.springboot2.config.JwtConfigProperties;
import com.myz.springboot2.security.JwtUser;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;

import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * @author maoyz
 */
public class JwtManager {

    private static JwtConfigProperties jwtConfigProperties;

    @Autowired
    public static void setJwtConfigProperties(JwtConfigProperties jwtConfigProperties) {
        JwtManager.jwtConfigProperties = jwtConfigProperties;
    }

    public static String generateToken(JwtUser jwtUser) {
        return generateToken(jwtUser.getUsername());
    }

    public static String generateToken(String username) {
        return jwtBuilderWithSign().setSubject(username).setExpiration(expirate(2)).compact();
    }

    public static String getUsernameFromToken(String token) {
        Jwt<Header, Claims> jwt = jwtParserWithSign()
                .parseClaimsJwt(token);
        String sub = jwt.getBody().getSubject();
        return sub;
    }

    public static Boolean validateToken(String authToken, UserDetails userDetails) {
        return true;
    }

    private static Date expirate(long hours) {
        if (hours == 0L) {
            hours = 2L;
        }
        LocalDateTime localDateTime = LocalDateTime.now().plusHours(jwtConfigProperties.getExpirationHours());
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    private static JwtBuilder jwtBuilderWithSign() {
        return Jwts.builder().signWith(key(), SignatureAlgorithm.HS512);
    }

    private static Key key() {
        return Keys.secretKeyFor(SignatureAlgorithm.HS512);
    }

    private static JwtParser jwtParserWithSign() {
        return Jwts.parser().setSigningKey(key());
    }

}

package com.smd.webapi.security;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.crypto.SecretKey;

import com.smd.webapi.model.JWTObject;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;

public class JWTCreator {
    public static final String HEADER_AUTHORIZATION = "Authorization";
    public static final String ROLES_AUTHORITIES = "authorities";

    public static String create(String prefix, SecretKey key, JWTObject jwtObject) {
        String token = Jwts
        		.builder()
        		.subject(jwtObject.getSubject())
        		.issuedAt(Date.from(jwtObject.getIssuedAt()))
        		.expiration(Date.from(jwtObject.getExpiration()))
                .claim(ROLES_AUTHORITIES, checkRoles(jwtObject.getRoles()))
                .signWith(key).compact();
        return prefix + " " + token;
    }
    
    public static JWTObject create(String token, String prefix, SecretKey key) throws ExpiredJwtException, UnsupportedJwtException, MalformedJwtException {
        JWTObject object = new JWTObject();
        
        token = token.replace(prefix + " ", "");
        Jws<Claims> claims = Jwts.parser().verifyWith(key).build().parseSignedClaims(token);
        
        object.setSubject(claims.getPayload().getSubject());
        object.setExpiration(claims.getPayload().getExpiration().toInstant());
        object.setIssuedAt(claims.getPayload().getIssuedAt().toInstant());
        object.setRoles((List<String>)claims.getPayload().get(ROLES_AUTHORITIES));

        return object;
    }
    
    private static List<String> checkRoles(List<String> roles) {
        return roles.stream().map(s -> "ROLE_".concat(s.replaceAll("ROLE_",""))).collect(Collectors.toList());
    }

}

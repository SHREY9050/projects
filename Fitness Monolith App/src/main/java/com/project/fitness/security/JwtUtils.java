package com.project.fitness.security;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;
import java.util.List;

@Component
// This Class is responsible to create Token,  Validate Token  and Loading Info into Token.
// I will call this class from different Places
public class JwtUtils {

    private String jwtSecret = "YS1zdHJpbmctc2VjcmV0LWF0LWxlYXN0LTI1Ni1iaXRzLWxvbmc=";
    private int jwtExpirationMs = 172800000 ;     // 48 hrs in ms



    // Retrieve the Token from Client with every request
    // To retrieve the Token we need to know in which format it is coming -> Authorization Bearer <Token>
    public String getJwtFromHeader(HttpServletRequest request){
        String bearerToken = request.getHeader("Authorization");
        if(bearerToken != null && bearerToken.startsWith("Bearer "))
            return bearerToken.substring(7);
        return null;
    }

    // When User Authenticate then Token needs to be generated
    // generate token by User Id


    public String generateToken(String userId , String role ){
        return Jwts.builder()
                .subject(userId)     // to whom it refers to
                .claim("roles", List.of(role))
                .issuedAt(new Date())
                .expiration(new Date(new Date().getTime() + jwtExpirationMs))
                .signWith(key())
                .compact();
    }

    private Key key(){
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }

    public boolean validateJwtToken(String jwtToken){

        try {
            Jwts.parser().verifyWith((SecretKey) key()).build().parseSignedClaims(jwtToken);
        } catch (Exception e){
            e.printStackTrace();
        }
        return true;
    }

    public String getUserIdFromToken(String jwt) {

        return Jwts.parser().verifyWith((SecretKey) key())
                .build().parseSignedClaims(jwt)
                .getPayload().getSubject();
    }

    public Claims getAllClaims(String jwt) {

        return Jwts.parser().verifyWith((SecretKey) key())
                .build().parseSignedClaims(jwt)
                .getPayload();
    }

    /**
     FLOW -
     1. If User Authenticated -> Generate Token
     2. Retrieve Token from User Request
     3. Validate Token
     */
}


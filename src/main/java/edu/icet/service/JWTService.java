package edu.icet.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class JWTService {

    private final SecretKey secretKey;

    public JWTService(){
        try {
            secretKey = Keys.hmacShaKeyFor(
                                KeyGenerator.getInstance("HmacSHA256")
                                .generateKey()
                                .getEncoded()
                        );
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    public String getToken(){
        return Jwts
                .builder()
                .subject("kaveesha")
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis()+1000*60*15))
                .signWith(secretKey)
                .compact();
    }

    public String getUsername(String token){
        return Jwts
                .parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }
}

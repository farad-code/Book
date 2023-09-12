package com.example.book.configuration;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class TokenService {

    public String extractUsername(String token){
        return extractClaim(token,Claims::getSubject);
    }

    public String getUsername(String token){
        return extractClaim(token,Claims::getSubject);
    }
    public Claims extractAllClaims(String token){
        return Jwts
                .parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public <T>  T extractClaim(String token, Function<Claims,T> claimResolver){
        Claims claims = extractAllClaims(token);
        return claimResolver.apply(claims);
    }

    public String generateAccessToken(Map<String,Object> extraClaims, UserDetails userDetails){

        return generateToken(extraClaims,userDetails,3600000);
    }

    public String generateRefreshToken(UserDetails userDetails){

        return generateToken(new HashMap<>(),userDetails,3600000);
    }

    public String generateAccessToken(UserDetails userDetails){
        return  generateAccessToken(new HashMap<>(), userDetails);
    }

    public boolean isTokenValid(String token,UserDetails userDetails){
        final String username = extractUsername(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    private String generateToken(Map<String,Object>extraClaims, UserDetails userDetails, long expiration){
        return    Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public Key getSigningKey(){
        byte[] key = Decoders.BASE64.decode("V+wmENLqBWLE9uE2X420AxFYpNScVn1ueQgwnrpDrsNhwtTqWVHp3d0l3aZ4RiWVNQ");
        return Keys.hmacShaKeyFor(key);
    }

}

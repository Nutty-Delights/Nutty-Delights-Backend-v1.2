package com.company.NuttyDelightsBackend.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Service
public class JwtProvider {

    SecretKey secretKey = Keys.hmacShaKeyFor(JWTConstant.SECRET_KEY.getBytes());
    public  String generateToken(Authentication authentication){

        String jwt = Jwts.builder()
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + 846000000))
                .claim("email" , authentication.getName())
                .signWith(secretKey).compact();
//        System.out.println(jwt);

        return  jwt;
    }

    public String getEmailFromToken(String jwt){
        String newJwt = jwt.split(" ")[1];
//        System.out.println("Jwt " + newJwt);
//        jwt = jwt.substring(7);
        Claims claims = Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(newJwt).getBody();
//        System.out.println(claims);
        String email = String.valueOf(claims.get("email"));
        return email;
    }

    public String populateAuthorities(Collection<? extends GrantedAuthority> collection) {
        Set<String> auths=new HashSet<>();

        for(GrantedAuthority authority:collection) {
            auths.add(authority.getAuthority());
        }
        return String.join(",",auths);
    }
}

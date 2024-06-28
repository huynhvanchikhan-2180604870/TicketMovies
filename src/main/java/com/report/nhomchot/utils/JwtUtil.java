package com.report.nhomchot.utils;

import com.report.nhomchot.entities.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.function.Function;

@Component
public class JwtUtil {
    private String SECRET_KEY =  "gKMg7mlxuikC45AC687aguPE9al2A5o4fDpNCgkc0Wwfijvncw5lzXdslGxljX7a";
    private long expireTime = 1000 * 60 * 60 * 10;
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        Claims claimList = Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
        return claimList;
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public String generateToken(User user) {
        Map<String, Object> claims = new HashMap<>();
        List<String> roleList = new ArrayList<>();
        //roleList.add("Admin");
        roleList.add("Customer");
        claims.put("role",roleList); // Thêm role vào claims

        List<String> claimList = new ArrayList<>();
        claimList.add("Product.ViewAll");
        claimList.add("Product.Delete");
        claimList.add("Product.Edit");

        claims.put("claim", claimList);
        return createToken(claims, user.getUsername());
    }

    private String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expireTime))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact();
    }

    public Boolean validateToken(String token) {
        final String username = extractUsername(token);
        return  !isTokenExpired(token);
    }

    public Claims getAllClaimsFromToken(String token) {
        return extractAllClaims(token);
    }
}


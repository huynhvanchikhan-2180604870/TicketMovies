//package com.report.nhomchot.utils.jwt;
//import com.report.nhomchot.entities.Role;
//import com.report.nhomchot.entities.User;
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.SignatureAlgorithm;
//import org.slf4j.LoggerFactory;
//import org.springframework.stereotype.Component;
//
//import java.util.*;
//import java.util.function.Function;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import java.util.stream.Collectors;
//
//@Component
//public class JwtUtil {
//    private static final Logger logger = LoggerFactory.getLogger(JwtUtil.class);
//    private String SECRET_KEY = "gKMg7mlxuikC45AC687aguPE9al2A5o4fDpNCgkc0Wwfijvncw5lzXdslGxljX7a";
//    private long expireTime = 1000 * 60 * 60 * 10;
//    public String extractUsername(String token) {
//        return extractClaim(token, Claims::getSubject);
//    }
//
//    public Date extractExpiration(String token) {
//        return extractClaim(token, Claims::getExpiration);
//    }
//
//    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
//        final Claims claims = extractAllClaims(token);
//        return claimsResolver.apply(claims);
//    }
//
//    private Claims extractAllClaims(String token) {
//        Claims claimList = Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
//        return claimList;
//    }
//
//    private Boolean isTokenExpired(String token) {
//        return extractExpiration(token).before(new Date());
//    }
//
//    public String generateToken(User user) {
//        try {
//            Map<String, Object> claims = new HashMap<>();
//            // Lấy danh sách tên vai trò từ các đối tượng Role của người dùng
//            List<String> roleList = user.getRoles().stream()
//                    .map(Role::getRoleName)
//                    .collect(Collectors.toList());
//
//            // Thêm danh sách vai trò vào claims
//            claims.put("roles", roleList);
//
//            // Tạo token với username và các claims đã thu thập
//            return createToken(claims, user.getUsername());
//        } catch (Exception e) {
//            logger.error("Error generating token for user: {}", user.getUsername(), e);
//            throw e;
//        }
//    }
//
//
//
//    private String createToken(Map<String, Object> claims, String subject) {
//        return Jwts.builder()
//                .setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
//                .setExpiration(new Date(System.currentTimeMillis() + expireTime))
//                .signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact();
//    }
//
//    public Boolean validateToken(String token) {
//        final String username = extractUsername(token);
//        return  !isTokenExpired(token);
//    }
//
//    public Claims getAllClaimsFromToken(String token) {
//        return extractAllClaims(token);
//    }
//}
package com.report.nhomchot.utils.jwt;

import com.report.nhomchot.entities.Role;
import com.report.nhomchot.entities.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class JwtUtil {
    private static final Logger logger = LoggerFactory.getLogger(JwtUtil.class);
    private String SECRET_KEY = "gKMg7mlxuikC45AC687aguPE9al2A5o4fDpNCgkc0Wwfijvncw5lzXdslGxljX7a";
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
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
    }

    public Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public List<String> extractRoles(String token) {
        Claims claims = extractAllClaims(token);
        return claims.get("roles", List.class);  // Assuming roles are stored under the "roles" claim
    }

    public String generateToken(User user) {
        try {
            Map<String, Object> claims = new HashMap<>();
            List<String> roleList = user.getRoles().stream()
                    .map(Role::getRoleName)
                    .collect(Collectors.toList());
            claims.put("roles", roleList);
            return createToken(claims, user.getUsername());
        } catch (Exception e) {
            logger.error("Error generating token for user: {}", user.getUsername(), e);
            throw e;
        }
    }

    private String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expireTime))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact();
    }

    public Boolean validateToken(String token) {
        return !isTokenExpired(token);
    }
}

package com.report.nhomchot.utils.jwt;

import com.report.nhomchot.utils.service.AuthenticateService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    private AuthenticateService authenticateService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserDetailsService userDetailsService;

//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
//            throws ServletException, IOException {
//        String url = request.getRequestURI();
//        if (isProtectedEndpoint(request.getRequestURI())) {
//            String authorizationHeader = request.getHeader("Authorization");
//            if(authorizationHeader == null || authorizationHeader.isBlank()){
//                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorizedq");
//                return;
//            }
//            String jwt  = authorizationHeader.substring(7);
//            String username = jwtUtil.extractUsername(jwt);
//            if (username == null) {
//                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized1");
//                return;
//            }
//            boolean validToken = jwtUtil.validateToken(jwt);
//            if(validToken == false ){
//                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token is expired");
//                return;
//            }
//            Claims claims = jwtUtil.getAllClaimsFromToken(jwt);
//            if(claims == null){
//                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token is expired");
//                return;
//            }
//            if(url.equals("/api/students/get-students")){
//                List<String> roleList = (List<String>) claims.get("role");
//                if(!(roleList.contains("admin"))){
//                    response.sendError(HttpServletResponse.SC_FORBIDDEN, "permission deny");
//                    return;
//                }
//            }
//        }
//        chain.doFilter(request, response);
//    }
@Override
protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
        throws ServletException, IOException {
    String authorizationHeader = request.getHeader("Authorization");

    if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
        String token = authorizationHeader.substring(7);

        if (!jwtUtil.isTokenExpired(token)) {
            String username = jwtUtil.extractUsername(token);

            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

                if (jwtUtil.validateToken(token)) {
                    List<SimpleGrantedAuthority> authorities = jwtUtil.extractRoles(token).stream()
                            .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                            .collect(Collectors.toList());

                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                            userDetails, null, authorities);
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        }
    }

    chain.doFilter(request, response);
}


    private boolean isProtectedEndpoint(String requestURI) {
        // Add your protected endpoints here
        List<String> accessDenyList = new ArrayList<String>();
//        accessDenyList.add("/api/products/get-productv3");
        // accessDenyList.add("/Admin/product-manage");
        accessDenyList.add("/api/students/get-students");

        //accessDenyList.add("/authenticate");
        return  accessDenyList.contains(requestURI); //requestURI.equals("/hello1") || requestURI.equals("/protected-endpoint2");
    }


}


//package com.report.nhomchot.config;
//
//import com.report.nhomchot.services.AuthenticateService;
//import com.report.nhomchot.utils.JwtUtil;
//import io.jsonwebtoken.Claims;
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//
//@Component
//public class JwtRequestFilter extends OncePerRequestFilter {
//
//    @Autowired
//    private AuthenticateService authenticateService;
//
//    @Autowired
//    private JwtUtil jwtUtil;
//
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
//                List<String> claimList = (List<String>) claims.get("claim");
//                if(!(roleList.contains("admin") || claimList.contains("Product.Edit"))){
//                    response.sendError(HttpServletResponse.SC_FORBIDDEN, "permission deny");
//                    return;
//                }
//            }
//        }
//        chain.doFilter(request, response);
//    }
//
//    private boolean isProtectedEndpoint(String requestURI) {
//        // Add your protected endpoints here
//        List<String> accessDenyList = new ArrayList<>();
////        accessDenyList.add("/api/products/get-productv3");
//        // accessDenyList.add("/Admin/product-manage");
//        accessDenyList.add("/api/students/get-students");
//
//        //accessDenyList.add("/authenticate");
//        return  accessDenyList.contains(requestURI); //requestURI.equals("/hello1") || requestURI.equals("/protected-endpoint2");
//    }
//
//
//}

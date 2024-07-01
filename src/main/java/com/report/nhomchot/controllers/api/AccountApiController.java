package com.report.nhomchot.controllers.api;




import com.report.nhomchot.dto.UserLoginDTO;
import com.report.nhomchot.entities.Role;
import com.report.nhomchot.entities.User;
import com.report.nhomchot.response.ResponseHandler;
import com.report.nhomchot.utils.jwt.JwtUtil;
import com.report.nhomchot.utils.repo.IUserService;
import com.report.nhomchot.utils.service.AuthenticateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/accounts")
public class AccountApiController {
    @Autowired
    AuthenticateService authenticateService;
    @Autowired
    private JwtUtil jwtTokenUtil;
    @Autowired
    private AuthenticationManager authenticationManager;

    public AccountApiController() {
    }

    @PostMapping("/insert-user")
    public ResponseEntity<Object> insertUser(@RequestBody User model){
        // Tạo UUID cho user mới
        model.setId(UUID.randomUUID());

        // Kiểm tra và bổ sung roleName cho từng Role
        if (model.getRoles() != null) {
            model.setRoles(model.getRoles().stream().map(role -> {
                if (role.getRoleName() == null || role.getRoleName().isEmpty()) {
                    throw new RuntimeException("Role name must not be null or empty");
                }
                return role;
            }).collect(Collectors.toSet()));
        }

        // Lưu user mới
        authenticateService.insertUser(model);
        return ResponseHandler.responseBuilder("success", HttpStatus.OK, model);
    }


    @RequestMapping(value = "get-users", method = RequestMethod.GET)
    public ResponseEntity<Object> getUser(){

        return ResponseHandler.responseBuilder("success",
                HttpStatus.OK,
                authenticateService.getAllUser()
        );
    }
    @RequestMapping(value = "get-roles", method = RequestMethod.GET)
    public ResponseEntity<Object> getRole(){

        return ResponseHandler.responseBuilder("success",
                HttpStatus.OK,
                authenticateService.getAllRole()
        );
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Map<String,Object> createAuthenticationToken(@RequestBody UserLoginDTO userLogin){
        Map<String, Object> result = new HashMap<>();
        result.put("status", true);
        User user = authenticateService.getUser(userLogin.getUsername(), userLogin.getPassword());
        if(user == null){
            result.put("message", "Can not login");
            result.put("status", false);
            return result;
        }
        String jwt = jwtTokenUtil.generateToken(user);
        result.put("message", "");
        result.put("token", jwt);
        result.put("role", user.getRoles().stream()
                .map(Role::getRoleName)
                .collect(Collectors.toList())); // Assuming you have a method to get roles
        return result;
    }

}

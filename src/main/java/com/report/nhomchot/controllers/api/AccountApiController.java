package com.report.nhomchot.controllers.api;

import com.report.nhomchot.entities.Role;
import com.report.nhomchot.entities.User;
import com.report.nhomchot.response.ResponseHandler;
import com.report.nhomchot.utils.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
public class AccountApiController{
    @Autowired
    private UserService userService;

    @GetMapping("/get-by-username/{username}")
    public ResponseEntity<User> getUserByUsername(@PathVariable String username) {
        User user = userService.findByUsername(username).orElse(null);
        Set<Role> roles = user.getRoles().stream()
                .map(role -> {
                    Role r = new Role();
                    r.setId(role.getId());
                    r.setName(role.getName());
                    return r;
                })
                .collect(Collectors.toSet());

        user.setRoles(roles);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/get-current-username")
    public ResponseEntity<String> getCurrentUsername(HttpSession session) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = null;
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            username = ((UserDetails) authentication.getPrincipal()).getUsername();
        } else if (authentication != null && authentication.getPrincipal() instanceof String) {
            username = (String) authentication.getPrincipal();
        }
        return ResponseEntity.ok(username);
    }
}
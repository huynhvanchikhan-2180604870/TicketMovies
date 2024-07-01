package com.report.nhomchot.utils.service;

import com.report.nhomchot.entities.Role;

import java.util.Set;
import java.util.UUID;

public class JwtResponse {
    private UUID id;
    private String accessToken;
    private String tokenType = "Bearer";
    private String username;
    private String name;
    private Set<Role> roles;
    public JwtResponse(String accessToken, UUID id, String username, String fullname,
                       Set<Role> set) {
        this.id = id;
        this.accessToken = accessToken;
        this.username = username;
        this.name = fullname;
        this.roles = set;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}

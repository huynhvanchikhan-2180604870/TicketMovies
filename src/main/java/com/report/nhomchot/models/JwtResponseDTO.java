package com.report.nhomchot.models;

import com.report.nhomchot.entities.Role;
import lombok.Data;

import java.util.Set;
import java.util.UUID;

@Data
public class JwtResponseDTO {
    private UUID id;
    private String username;
    private String name;
    private String accessToken;
    private String tokenType;
    private Set<Role> roles;
}

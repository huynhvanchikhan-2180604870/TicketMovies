package com.report.nhomchot.models;

import com.report.nhomchot.entities.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.Set;
import java.util.UUID;

import static lombok.AccessLevel.PRIVATE;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = PRIVATE)
public class JwtResponseDTO {
    UUID id;
    String accessToken;
    String username;
    Set<String> roles;
    String errorMessage; // Optional field for error messages

    // Convenience constructor for error messages
    public JwtResponseDTO(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}


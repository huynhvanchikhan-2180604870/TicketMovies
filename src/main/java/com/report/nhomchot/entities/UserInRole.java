package com.report.nhomchot.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Setter
@Getter
@RequiredArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "user_in_role")
public class UserInRole {
    @Id
    private UUID id;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user; // Người dùng

    @ManyToOne
    @JoinColumn(name = "role_id", nullable = false)
    private Role role; // Vai trò
}

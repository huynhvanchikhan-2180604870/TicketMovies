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
@Table(name = "role_in_claim")
public class RoleInClaim {
    @Id
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "role_id", nullable = false)
    private Role role; // Vai trò

    @ManyToOne
    @JoinColumn(name = "claim_id", nullable = false)
    private Claim claim; // Quyền
}

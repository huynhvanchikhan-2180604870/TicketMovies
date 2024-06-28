package com.report.nhomchot.entities;


import jakarta.persistence.*;
import lombok.*;

import java.util.Set;
import java.util.UUID;

@Setter
@Getter
@RequiredArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "claims")
public class Claim {
    @Id
    private UUID id;
    @Column(nullable = false)
    private String claimType;

    @Column(nullable = false)
    private String claimValue;

    @OneToMany(mappedBy = "claim")
    private Set<RoleInClaim> roleClaims;

}

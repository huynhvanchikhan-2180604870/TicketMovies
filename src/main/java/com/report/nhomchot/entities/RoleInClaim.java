package com.report.nhomchot.entities;

import com.report.nhomchot.models.RoleInClaimId;
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
    @EmbeddedId
    private RoleInClaimId id;

    @ManyToOne
    @MapsId("roleId")
    @JoinColumn(name = "role_id")
    private Role role;

    @ManyToOne
    @MapsId("claimId")
    @JoinColumn(name = "claim_id")
    private Claim claim;
}

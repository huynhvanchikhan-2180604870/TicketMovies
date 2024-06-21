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
    private UUID role_id;
    private UUID claim_id;

}

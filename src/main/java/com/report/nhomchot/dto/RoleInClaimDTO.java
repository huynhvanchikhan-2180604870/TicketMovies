package com.report.nhomchot.dto;

import lombok.*;

import java.util.UUID;

@Setter
@Getter
@RequiredArgsConstructor
@AllArgsConstructor
@Data
public class RoleInClaimDTO {
    private UUID roleId;
    private UUID claimId;
}

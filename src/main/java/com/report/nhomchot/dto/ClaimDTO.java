package com.report.nhomchot.dto;

import lombok.*;

import java.util.UUID;

@Setter
@Getter
@RequiredArgsConstructor
@AllArgsConstructor
@Data
public class ClaimDTO {
    private UUID id;
    private String claimType;
    private String claimValue;
}

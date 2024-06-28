package com.report.nhomchot.dto;

import lombok.*;

import java.util.Set;
import java.util.UUID;

@Setter
@Getter
@RequiredArgsConstructor
@AllArgsConstructor
@Data
public class CategoryDTO {
    private UUID id;
    private String name;
    private int age_accept;
    private Set<UUID> movieIds;
}

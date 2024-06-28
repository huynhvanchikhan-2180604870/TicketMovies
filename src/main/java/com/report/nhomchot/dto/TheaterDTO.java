package com.report.nhomchot.dto;

import lombok.*;

import java.util.UUID;

@Setter
@Getter
@RequiredArgsConstructor
@AllArgsConstructor
@Data
public class TheaterDTO {
    private UUID id;
    private String name;
    private UUID cinema_id;
    private Integer seating_capacity;

    public TheaterDTO(UUID id, String name) {
        this.id = id;
        this.name = name;
    }
}

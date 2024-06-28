package com.report.nhomchot.dto;

import lombok.*;

import java.util.UUID;

@Setter
@Getter
@RequiredArgsConstructor
@AllArgsConstructor
@Data
public class CinemaDTO {
    private UUID id;
    private String name;
    private String location;
    private Integer total_theaters;
}

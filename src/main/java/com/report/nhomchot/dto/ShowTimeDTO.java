package com.report.nhomchot.dto;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;

@Setter
@Getter
@RequiredArgsConstructor
@AllArgsConstructor
@Data
public class ShowTimeDTO {
    private UUID id;
    private UUID theater_id;
    private UUID movie_id;
    private LocalDate startTime;
    private LocalTime endTime;
    private Integer price;
}

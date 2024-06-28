package com.report.nhomchot.dto;

import lombok.*;

import java.time.LocalDateTime;
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
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Integer price;
}

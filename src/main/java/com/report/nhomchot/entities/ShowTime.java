package com.report.nhomchot.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Setter
@Getter
@RequiredArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "showtimes")
public class ShowTime {
    @Id
    private UUID id;
    private UUID theater_id;
    private UUID movie_id;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Integer price;
}

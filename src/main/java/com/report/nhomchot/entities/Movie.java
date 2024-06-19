package com.report.nhomchot.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Setter
@Getter
@RequiredArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "movies")
public class Movie {
    @Id
    private UUID id;
    private String title;
    private String description;
    private Integer director;
    private LocalDateTime release_date;
}

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
@Table(name = "movies")
public class Movie {
    @Id
    private UUID id;
    private String title;
    private String description;
    private Integer director;
    private LocalDateTime release_date;
    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;
}

package com.report.nhomchot.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
@RequiredArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Data
@Entity
@Table(name = "movies")
public class Movie {
    @Id
    private UUID id;
    private String title;
    private String description;
    private Integer duration;
    private LocalDate release_date;
    private String poster_url;
    private UUID category_id;
    private String director;
    private String authors;
}

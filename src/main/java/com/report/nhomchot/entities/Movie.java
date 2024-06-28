package com.report.nhomchot.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
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
    private String director;
    private String authors;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @OneToMany(mappedBy = "movie")
    private Set<ShowTime> showtimes;
}

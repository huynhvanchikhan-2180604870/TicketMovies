package com.report.nhomchot.dto;

import lombok.*;

import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

@Setter
@Getter
@RequiredArgsConstructor
@AllArgsConstructor
@Data
public class MovieDTO {
    private UUID id;
    private String title;
    private String description;
    private Integer duration;
    private LocalDate release_date;
    private String poster_url;
    private String director;
    private String authors;
    private UUID category_id; // IDs of categories related to the movie
}

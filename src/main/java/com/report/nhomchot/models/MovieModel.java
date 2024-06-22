package com.report.nhomchot.models;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@RequiredArgsConstructor
@AllArgsConstructor
public class MovieModel {
    private UUID id;
    private String title;
    private String description;
    private Integer duration;
    private LocalDate release_date;
    private UUID category_id;
    private String poster_url;
    private String director;
    private String authors;
}

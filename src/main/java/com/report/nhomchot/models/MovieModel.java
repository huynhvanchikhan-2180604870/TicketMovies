package com.report.nhomchot.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Setter
@Getter
@RequiredArgsConstructor
@AllArgsConstructor
public class MovieModel {
    private UUID id;
    private String title;
    private String description;
    private Integer director;
    private LocalDateTime release_date;
    private UUID category_id;
    private String poster_url;

}

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
public class ShowTimeModel {
    private UUID id;
    private UUID theater_id;
    private UUID movie_id;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Integer price;
}

package com.report.nhomchot.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.UUID;
@Setter
@Getter
@RequiredArgsConstructor
@AllArgsConstructor
public class TheaterModel {
    private UUID id;
    private UUID cinema_id;
    private Integer seating_capacity;
}

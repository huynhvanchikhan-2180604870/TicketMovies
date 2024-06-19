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
public class CinemaModel {
    private UUID id;
    private String name;
    private String location;
    private Integer total_theaters;
}

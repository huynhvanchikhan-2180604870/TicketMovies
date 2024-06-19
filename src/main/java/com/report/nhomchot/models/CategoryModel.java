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
public class CategoryModel {
    private UUID id;
    private String name;
    private Integer age_accept;
}

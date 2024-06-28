package com.report.nhomchot.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;
import java.util.UUID;
@Setter
@Getter
@RequiredArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "categories")
public class Category {
    @Id
    private UUID id;
    private String name;
    private int age_accept;
    @OneToMany(mappedBy = "category")
    private Set<Movie> movies;
}

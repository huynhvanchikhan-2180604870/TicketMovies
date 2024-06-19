package com.report.nhomchot.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.util.UUID;

@Setter
@Getter
@RequiredArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "cinemas")
public class Cinema {
    @Id
    private UUID id;
    private String name;
    private String location;
    private Integer total_theaters;
}

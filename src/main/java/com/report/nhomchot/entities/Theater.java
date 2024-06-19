package com.report.nhomchot.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Setter
@Getter
@RequiredArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "theaters")
public class Theater {
    @Id
    private UUID id;
    @ManyToOne
    @JoinColumn(name = "cinema_id", nullable = false)
    private Cinema cinema;
    private Integer seating_capacity;
}

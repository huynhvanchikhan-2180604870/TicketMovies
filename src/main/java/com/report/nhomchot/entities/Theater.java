package com.report.nhomchot.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
@Table(name = "theaters")
public class Theater {
    @Id
    private UUID id;
    private String name;
    @ManyToOne
    @JoinColumn(name = "cinema_id", nullable = false)
    private Cinema cinema;
    private Integer seating_capacity;

    @OneToMany(mappedBy = "theater")
    private Set<ShowTime> showtimes;

    @OneToMany(mappedBy = "theater")
    private Set<Seat> seats;
}

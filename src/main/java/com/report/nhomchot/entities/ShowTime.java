package com.report.nhomchot.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Setter
@Getter
@RequiredArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "showtimes")
public class ShowTime {
    @Id
    private UUID id;
    @ManyToOne
    @JoinColumn(name = "theater_id")
    public Theater theater;

    @ManyToOne
    @JoinColumn(name = "movie_id")
    public Movie movie;
    public LocalDateTime startTime;
    public LocalDateTime endTime;
    public Integer price;

    @OneToMany(mappedBy = "showtime")
    public Set<Ticket> tickets;
}

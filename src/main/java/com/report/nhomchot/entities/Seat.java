package com.report.nhomchot.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.Set;
import java.util.UUID;

@Setter
@Getter
@RequiredArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "seats")
public class Seat {
    @Id
    private UUID id;
    private String name;
    private boolean isVip;
    private boolean isActive;
    @ManyToOne
    @JoinColumn(name = "theater_id",nullable = false)
    private Theater theater;

    @OneToMany(mappedBy = "seat")
    private Set<Ticket> tickets;
}

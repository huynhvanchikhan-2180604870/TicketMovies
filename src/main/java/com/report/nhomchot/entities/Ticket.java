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
@Table(name = "tickets")
public class Ticket {
    @Id
    private UUID id;
    private UUID showtime_id;
    private UUID user_id;
    private Integer seatNumber;
    private short status;
}

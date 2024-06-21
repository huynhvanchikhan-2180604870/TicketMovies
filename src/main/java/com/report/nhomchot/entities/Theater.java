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
    private UUID cinema_id;
    private Integer seating_capacity;
}

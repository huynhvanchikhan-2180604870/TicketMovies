package com.report.nhomchot.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.*;

import java.util.Set;
import java.util.UUID;

@Setter
@Getter
@RequiredArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "roles")
public class Role {
    @Id
    private UUID id;
    private String name;
}

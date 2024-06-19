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
@Table(name = "claims")
public class Claim {
    @Id
    private UUID id;
    private String name; // Tên quyền
    private String description; // Mô tả quyền
}

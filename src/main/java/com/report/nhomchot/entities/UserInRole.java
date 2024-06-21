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
@Table(name = "user_in_role")
public class UserInRole {
    @Id
    private UUID id;
    private UUID user_id;
    private UUID role_id;

}

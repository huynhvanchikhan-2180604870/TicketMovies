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
@Table(name = "users")
public class User {
    @Id
    private UUID id;
    private String username;
    private String password;
    private String address;
    private String email;
    private String phone;
    private Integer age;
    @OneToMany(mappedBy = "user")
    private Set<UserInRole> userRoles;

}

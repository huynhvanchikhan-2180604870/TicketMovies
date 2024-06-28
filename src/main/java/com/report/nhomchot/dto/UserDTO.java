package com.report.nhomchot.dto;

import lombok.*;

import java.util.UUID;

@Setter
@Getter
@RequiredArgsConstructor
@AllArgsConstructor
@Data
public class UserDTO {
    private UUID id;
    private String username;
    private String address;
    private String email;
    private String phone;
    private Integer age;
}

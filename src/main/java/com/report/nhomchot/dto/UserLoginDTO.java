package com.report.nhomchot.dto;

import lombok.*;

@Setter
@Getter
@RequiredArgsConstructor
@AllArgsConstructor
@Data
public class UserLoginDTO {
    private String username;
    private String password;
}

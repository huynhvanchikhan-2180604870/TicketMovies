package com.report.nhomchot.dto;

import lombok.*;

import java.util.UUID;

@Setter
@Getter
@RequiredArgsConstructor
@AllArgsConstructor
@Data
public class SeatDTO {
    private UUID id;
    private String name;
    private boolean isActive;
    private boolean isVip;
    private int isOccupied;
    private boolean isChecked;
    private TheaterDTO theater;
}

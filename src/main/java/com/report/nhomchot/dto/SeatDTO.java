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
    private boolean isOccupied = false;
    private boolean isChecked;
    private TheaterDTO theater;

    public String getCssClass() {
        if (!this.isActive) {
            return "item--hidden";
        } else if (this.isChecked) {
            return "item--checked";
        } else if (this.isOccupied) {
            return "item--picked";
        } else if (this.isVip) {
            return "item--vip";
        }
        return "item--regular";
    }

}

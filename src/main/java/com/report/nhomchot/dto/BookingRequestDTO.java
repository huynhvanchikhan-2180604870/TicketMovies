package com.report.nhomchot.dto;

import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class BookingRequestDTO {
    private UUID user_id;
    private UUID theater_id;
    private List<UUID> list_seat_id;
}

package com.report.nhomchot.services;

import com.report.nhomchot.dto.SeatDTO;

import java.util.List;
import java.util.UUID;

public interface ISeatService {
    List<SeatDTO> getSeatsByShowTimeAndUserId(UUID showtime_id, UUID userId);
    List<SeatDTO> getAllSeatByTheater(UUID theater_id);
    void addAllSeat(List<SeatDTO> seats,UUID theater_id);
    void updateSeats(List<SeatDTO> seats);
}

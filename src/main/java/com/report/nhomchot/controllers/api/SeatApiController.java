package com.report.nhomchot.controllers.api;

import com.report.nhomchot.dto.SeatDTO;
import com.report.nhomchot.services.ISeatService;
import com.report.nhomchot.services.SeatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/api/seats")
public class SeatApiController {
    @Autowired
    private ISeatService seatService;

    @GetMapping
    public List<SeatDTO> getSeatsByScheduleId(@RequestParam UUID showtime_id,@RequestParam UUID userId){
        return seatService.getSeatsByShowTimeAndUserId(showtime_id,userId);
    }
    @PostMapping
    public List<SeatDTO> getSeatsByRoomId(@RequestParam UUID theater_id){
        return seatService.getAllSeatByTheater(theater_id);
    }
}

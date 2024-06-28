package com.report.nhomchot.controllers.api;

import com.report.nhomchot.dto.SeatDTO;
import com.report.nhomchot.services.SeatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/seats")
public class SeatApiController {
//    @Autowired
//    private SeatService seatService;
//
////    @GetMapping
////    public List<SeatDTO> getSeatsByScheduleId(@RequestParam UUID theaterID, @RequestParam UUID userId){
////        return seatService.getSeatsByTheaterIdAndUserId(theaterID,userId);
////    }
//    @PostMapping
//    public List<SeatDTO> getSeatsBytheaterID(@RequestParam UUID theaterID){
//        return seatService.getAllSeatByTheater(theaterID);
//    }
}

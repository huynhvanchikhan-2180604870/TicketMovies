package com.report.nhomchot.controllers;

import com.report.nhomchot.dto.SeatDTO;
import com.report.nhomchot.dto.ShowTimeDTO;
import com.report.nhomchot.dto.TheaterDTO;
import com.report.nhomchot.entities.Seat;
import com.report.nhomchot.entities.ShowTime;
import com.report.nhomchot.entities.Theater;
import com.report.nhomchot.models.JwtResponseDTO;
import com.report.nhomchot.services.SeatService;
import com.report.nhomchot.services.ShowTimeService;
import com.report.nhomchot.utils.Api;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping("/showtimes")
public class ShowTimeController {
    @Autowired
    private ShowTimeService showTimeService;


    @GetMapping
    public ShowTimeDTO getSchedule(@RequestParam UUID scheduleId){
        return showTimeService.findById(scheduleId).orElseThrow();
    }
    @GetMapping("/all-schedule-dates")
    public List<String> getAllStartDate(){
        return  showTimeService.getAllStartDateShowTime();
    }

    @RequestMapping(value = "/show-detail/{id}", method = RequestMethod.GET)
    public String showDetail(@PathVariable UUID id, Model model){
        ShowTime showTime = showTimeService.getShowTimeById(id).orElseThrow();

        model.addAttribute("showtime", showTime);
        return "showtime/detail";
    }
}

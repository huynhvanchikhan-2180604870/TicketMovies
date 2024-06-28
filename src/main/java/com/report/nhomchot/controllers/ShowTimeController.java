package com.report.nhomchot.controllers;

import com.report.nhomchot.dto.SeatDTO;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
    @Autowired
    private SeatService seatService;

    public static String API_GET_SCHEDULE= Api.baseURL+"/api/theaters";
    public static String API_GET_SEATS= Api.baseURL+"/api/seats";
    public ShowTimeController(){}

    @RequestMapping(value = "/show-detail/{id}", method = RequestMethod.GET)
    public String showDetail(@PathVariable UUID id, Model model){
        ShowTime showTime = showTimeService.getShowTimeById(id).orElseThrow();

        model.addAttribute("showtime", showTime);
        return "showtime/detail";
    }

    @RequestMapping(value = "booking/{id}", method = RequestMethod.GET)
    public String showChooseSeat(@PathVariable UUID id, HttpServletRequest request, Model model) {
        ShowTime showTime = showTimeService.getShowTimeById(id).orElseThrow();
        List<SeatDTO> seats = seatService.getAllSeatDTOByTheater(showTime.getTheater().getId());

        model.addAttribute("showtime", showTime);
        model.addAttribute("seats", seats);
        return "showtime/choose-seat";
    }
}

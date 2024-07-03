package com.report.nhomchot.controllers;

import java.util.*;

import com.report.nhomchot.dto.SeatDTO;
import com.report.nhomchot.dto.ShowTimeDTO;
import com.report.nhomchot.entities.Seat;
import com.report.nhomchot.entities.ShowTime;
import com.report.nhomchot.entities.User;
import com.report.nhomchot.models.JwtResponseDTO;
import com.report.nhomchot.services.SeatService;
import com.report.nhomchot.services.ShowTimeService;
import com.report.nhomchot.utils.Api;
import com.report.nhomchot.utils.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Controller
@RequestMapping("/booking")
public class BookingController {
    private final WebClient webClient;
    private final UserService userService;
    private final ShowTimeService showTimeService;
    private final SeatService seatService;
    @Autowired
    public BookingController(WebClient webClient,
                             UserService userService,
                             ShowTimeService showTimeService,
                             SeatService seatService
    ) {
        this.webClient = webClient;
        this.userService = userService;
        this.showTimeService = showTimeService;
        this.seatService = seatService;
    }

    @GetMapping("/{id}")
    public String displayRoomAndSeat(@PathVariable("id") UUID id, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = null;
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            username = ((UserDetails) authentication.getPrincipal()).getUsername();
        } else if (authentication != null && authentication.getPrincipal() instanceof String) {
            username = (String) authentication.getPrincipal();
        }

        if (username == null) {
            return "redirect:/login";
        }

        User user = userService.findByUsername(username).orElse(null);

        if (user == null) {
            return "redirect:/login";
        }

        ShowTime showtime =showTimeService.getShowTimeById(id).orElseThrow();

        model.addAttribute("showtime", showtime);

        List<SeatDTO> seats = seatService.getSeatsByShowTimeAndUserId(id, user.getId());

        model.addAttribute("seats", seats);

        return "showtime/choose-seat";
    }
}
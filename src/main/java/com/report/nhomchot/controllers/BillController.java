package com.report.nhomchot.controllers;

import com.report.nhomchot.dto.BookingRequestDTO;
import com.report.nhomchot.dto.ShowTimeDTO;
import com.report.nhomchot.entities.User;
import com.report.nhomchot.models.JwtResponseDTO;
import com.report.nhomchot.utils.Api;
import com.report.nhomchot.utils.config.VNPayService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/bill")
public class BillController {

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private VNPayService vnPayService;


    private static final String API_CREATE_BILL = Api.baseURL + "/api/bills/create-new-bill";

    @PostMapping
    public String displayBill(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession();
        String[] seats = request.getParameterValues("seats");

        if (seats == null || seats.length == 0) {
            session.setAttribute("bookedError", "No seats selected.");
            return "redirect:/seat-selection"; // Adjust this URL based on your application flow
        }

        List<Integer> listSeatIds = Arrays.stream(seats)
                .map(Integer::parseInt)
                .collect(Collectors.toList());
        session.setAttribute("listSelectedSeatIds", listSeatIds);

        Integer numberOfSelectedSeats = listSeatIds.size();
        model.addAttribute("numberOfSelectedSeats", numberOfSelectedSeats);

        ShowTimeDTO showtimeFromSession = (ShowTimeDTO) session.getAttribute("showtime");
        if (showtimeFromSession == null) {
            session.setAttribute("bookedError", "Showtime information is missing.");
            return "redirect:/seat-selection"; // Adjust this URL based on your application flow
        }

        Integer totalAmount = showtimeFromSession.getPrice() * numberOfSelectedSeats;
        model.addAttribute("totalAmount", totalAmount);

        model.addAttribute("formattedDate",
                showtimeFromSession.getStartTime().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));

        model.addAttribute("user", new User());
        session.removeAttribute("bookedError");
        return "bill";
    }

    @GetMapping
    public String createBill(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession();
        JwtResponseDTO jwtResponseDTO = (JwtResponseDTO) session.getAttribute("jwtResponse");

        if (jwtResponseDTO == null) {
            session.setAttribute("bookedError", "User is not authenticated.");
            return "redirect:/login"; // Redirect to login page
        }

        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
        headers.set(HttpHeaders.AUTHORIZATION, "Bearer " + jwtResponseDTO.getAccessToken());

        BookingRequestDTO body = new BookingRequestDTO();
        body.setUser_id(jwtResponseDTO.getId());

        ShowTimeDTO showtimeFromSession = (ShowTimeDTO) session.getAttribute("showtime");
        if (showtimeFromSession == null) {
            session.setAttribute("bookedError", "Showtime information is missing.");
            return "redirect:/seat-selection"; // Adjust this URL based on your application flow
        }

        body.setShowtime_id(showtimeFromSession.getId());
        List<UUID> listSeatIds = (List<UUID>) session.getAttribute("listSelectedSeatIds");
        if (listSeatIds == null || listSeatIds.isEmpty()) {
            session.setAttribute("bookedError", "No seats selected.");
            return "redirect:/seat-selection"; // Adjust this URL based on your application flow
        }

        body.setList_seat_id(listSeatIds);
        model.addAttribute("user", new User());

        try {
            HttpEntity<BookingRequestDTO> entity = new HttpEntity<>(body, headers);
            ResponseEntity<String> response = restTemplate.exchange(API_CREATE_BILL, HttpMethod.POST, entity, String.class);
            if (response.getStatusCode() == HttpStatus.OK) {
                return "redirect:/tickets/history";
            } else {
                session.setAttribute("bookedError", "Failed to create bill.");
                return "redirect:/seat-selection"; // Adjust this URL based on your application flow
            }
        } catch (HttpClientErrorException ex) {
            session.setAttribute("bookedError", ex.getResponseBodyAsString());
            return "redirect:/seat-selection?movieId=" + showtimeFromSession.getMovie_id() +
                    "&startDate=" + showtimeFromSession.getStartTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) +
                    "&startTime=" + showtimeFromSession.getStartTime().format(DateTimeFormatter.ofPattern("HH:mm")) +
                    "&roomId=" + showtimeFromSession.getTheater_id();
        }
    }

    @GetMapping("/payment-callback")
    public String getPaymentCallback(HttpServletRequest request, Model model){
        int paymentStatus =vnPayService.orderReturn(request);

        String orderInfo = request.getParameter("vnp_OrderInfo");
        String paymentTime = request.getParameter("vnp_PayDate");
        String transactionId = request.getParameter("vnp_TransactionNo");
        String totalPrice = request.getParameter("vnp_Amount");

        model.addAttribute("orderId", orderInfo);
        model.addAttribute("totalPrice", totalPrice);
        model.addAttribute("paymentTime", paymentTime);
        model.addAttribute("transactionId", transactionId);

        return paymentStatus == 1 ? "home/ordersuccess" : "home/orderfail";
    }
}

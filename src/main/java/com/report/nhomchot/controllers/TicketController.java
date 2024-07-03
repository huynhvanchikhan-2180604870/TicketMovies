package com.report.nhomchot.controllers;

import com.report.nhomchot.dto.TicketDTO;
import com.report.nhomchot.entities.Ticket;
import com.report.nhomchot.entities.User;
import com.report.nhomchot.services.TicketService;
import com.report.nhomchot.utils.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("ticket")
public class TicketController {
    @Autowired
    private TicketService ticketService;
    @Autowired
    private UserService userService;

    @GetMapping("/history")
    public String ShowOrderHistory(Model model){
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

        List<Ticket> tickets = ticketService.getTicketOfUser(user.getId());
        model.addAttribute("tickets", tickets);
        return "ticket/history";
    }
}

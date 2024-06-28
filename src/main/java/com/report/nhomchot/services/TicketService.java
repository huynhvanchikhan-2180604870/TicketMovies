package com.report.nhomchot.services;

import com.report.nhomchot.entities.Seat;
import com.report.nhomchot.entities.Theater;
import com.report.nhomchot.entities.Ticket;
import com.report.nhomchot.repositories.ITicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TicketService {
    @Autowired
    private ITicketRepository ticketRepository;

    public TicketService(){}

    public List<Ticket> getAllTicket(){
        return ticketRepository.findAll();
    }

    public Optional<Ticket> getTicketById(UUID id){
        return ticketRepository.findById(id);
    }

    public Ticket addTicket(Ticket ticket){
        return ticketRepository.save(ticket);
    }

//    public Ticket updateTicket(Ticket ticket){
//        Ticket existingTicket = ticketRepository.findById(ticket.getId())
//                .orElseThrow(() -> new IllegalStateException("ticket with ID " +
//                        ticket.getId() + " does not exist."));
//        existingTicket.setShowtime(ticket.getShowtime());
//        existingTicket.setUser(ticket.getUser());
//        existingTicket.setStatus(ticket.getStatus());
//        existingTicket.setBookingTime(ticket.getBookingTime());
//        existingTicket.setSeat(ticket.getSeat());
//        return ticketRepository.save(existingTicket);
//    }

    public void deleteTicket(UUID id){
        if (!ticketRepository.existsById(id)) {
            throw new IllegalStateException("Ticket with ID " + id + " does not exist.");
        }
        ticketRepository.deleteById(id);
    }

    public List<Seat>findTicketsByTheater_Id(UUID id){
        return findTicketsByTheater_Id(id);
    }
}

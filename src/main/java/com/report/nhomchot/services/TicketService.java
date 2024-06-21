package com.report.nhomchot.services;

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

    public Ticket updateTicket(Ticket ticket){
        Ticket existingTicket = ticketRepository.findById(ticket.getId())
                .orElseThrow(() -> new IllegalStateException("ticket with ID " +
                        ticket.getId() + " does not exist."));
        existingTicket.setShowtime_id(ticket.getShowtime_id());
        existingTicket.setUser_id(ticket.getShowtime_id());
        existingTicket.setStatus(ticket.getStatus());
        existingTicket.setSeatNumber(ticket.getSeatNumber());
        return ticketRepository.save(existingTicket);
    }

    public void deleteTicket(UUID id){
        if (!ticketRepository.existsById(id)) {
            throw new IllegalStateException("Ticket with ID " + id + " does not exist.");
        }
        ticketRepository.deleteById(id);
    }
}

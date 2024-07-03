package com.report.nhomchot.services;

import com.report.nhomchot.dto.TicketDTO;
import com.report.nhomchot.entities.Seat;
import com.report.nhomchot.entities.Theater;
import com.report.nhomchot.entities.Ticket;
import com.report.nhomchot.repositories.ITicketRepository;
import com.report.nhomchot.utils.repo.IGeneralService;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class TicketService implements IGeneralService<TicketDTO>, ITicketService {
    @Autowired
    private ITicketRepository ticketRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Override
    public List<TicketDTO> getTicketsByUserId(UUID userId) {
        return ticketRepository.findTicketsByUserId(userId)
                .stream().map(ticket -> modelMapper.map(ticket,TicketDTO.class))
                .collect(Collectors.toList());
    }
    @Override
    public List<TicketDTO> getTicketsByBillId(UUID billId) {
        return ticketRepository.findTicketsByBill_Id(billId)
                .stream().map(ticket -> modelMapper.map(ticket,TicketDTO.class))
                .collect(Collectors.toList());
    }
    @Override
    public List<TicketDTO> findAll() {
        return ticketRepository.findAll()
                .stream().map(ticket -> modelMapper.map(ticket,TicketDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public TicketDTO save(TicketDTO ticketDTO) {
        Ticket ticket = modelMapper.map(ticketDTO, Ticket.class);
        Ticket savedTicket = ticketRepository.save(ticket);
        return modelMapper.map(savedTicket, TicketDTO.class);
    }

    public List<Ticket> getTicketOfUser(UUID id){
        return ticketRepository.findTicketsByUserId(id)
                .stream().map(ticket -> modelMapper.map(ticket,Ticket.class))
                .collect(Collectors.toList());
    }

    @Override
    public void remove(UUID id) {
        ticketRepository.deleteById(id);
    }


    @Override
    public Optional<TicketDTO> findById(UUID id) {
        Optional<Ticket> ticket = ticketRepository.findById(id);
        return ticket.map(t -> modelMapper.map(t, TicketDTO.class));
    }



}

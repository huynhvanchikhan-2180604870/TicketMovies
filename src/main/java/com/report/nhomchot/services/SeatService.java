package com.report.nhomchot.services;

import com.report.nhomchot.dto.SeatDTO;
import com.report.nhomchot.dto.TheaterDTO;
import com.report.nhomchot.entities.Seat;
import com.report.nhomchot.entities.Theater;
import com.report.nhomchot.repositories.ISeatRepository;
import com.report.nhomchot.repositories.IShowTimeRepository;
import com.report.nhomchot.repositories.ITheaterRepository;
import com.report.nhomchot.repositories.ITicketRepository;
import jakarta.persistence.SecondaryTable;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class SeatService implements ISeatService{
    @Autowired
    private ISeatRepository seatRepository;
    @Autowired
    private IShowTimeRepository showTimeRepository;
    @Autowired
    private ITicketRepository ticketRepository;
    @Autowired
    private ModelMapper modelMapper;


    public List<Seat> getAll(){
        return seatRepository.findAll();
    }


    @Override
    public List<SeatDTO> getSeatsByShowTimeAndUserId(UUID showtime_id,UUID userId) {
        // Lấy ra các chỗ ngồi của phòng trong lịch đó
        Theater theater = showTimeRepository.findById(showtime_id).get().theater;
        List<Seat> listSeat = seatRepository.getSeatByTheater_Id(theater.getId());

        // Lấy ra các vé đã được đặt trong lịch đó rồi map sang các chỗ ngồi
        List<Seat> occupiedSeats = ticketRepository.findTicketsByShowtime_Id(showtime_id)
                .stream().map(ticket -> ticket.getSeat())
                .collect(Collectors.toList());
        // Lấy ra các vé người dùng đã đặt trong lịch đó rồi map sang các chỗ ngồi
        List<Seat> checkedSeats= ticketRepository.findTicketsByUserIdAndScheduleId(userId,showtime_id)
                .stream().map(ticket -> ticket.getSeat())
                .collect(Collectors.toList());
        // Map list chỗ ngồi của phòng ở bước 1 sang list dto
        List<SeatDTO> filteredSeats = listSeat.stream().map(seat -> {
            SeatDTO seatDTO = modelMapper.map(seat,SeatDTO.class);
            if(occupiedSeats.stream()
                    .map(occupiedSeat->occupiedSeat.getId())
                    .collect(Collectors.toList()).contains(seat.getId())){
                seatDTO.setOccupied(true); // Nếu ghế nào nằm trong list ghế đã được occupied thì set = 1
            }
            return seatDTO;
        }).collect(Collectors.toList());
        filteredSeats = filteredSeats.stream().map(seat -> {
            SeatDTO seatDTO = modelMapper.map(seat,SeatDTO.class);
            if(checkedSeats.stream()
                    .map(checkedSeat->checkedSeat.getId())
                    .collect(Collectors.toList()).contains(seat.getId())){
                seatDTO.setChecked(true); // Nếu ghế nào nằm trong list ghế đã được người dùng đặt thì set = true
            }
            return seatDTO;
        }).collect(Collectors.toList());
        return  filteredSeats;
    }

    @Override
    public List<SeatDTO> getAllSeatByTheater(UUID roomId) {
        return seatRepository.getSeatByTheater_Id(roomId).stream()
                .map(seat-> modelMapper.map(seat, SeatDTO.class)).collect(Collectors.toList());
    }

    @Override
    public void addAllSeat(List<SeatDTO> seats,UUID roomId) {
        seatRepository.saveAll(seats.stream().map(
                seat->{
                    Seat seatEntity=modelMapper.map(seat, Seat.class);
                    seatEntity.setTheater(new Theater());
                    seatEntity.getTheater().setId(roomId);
                    return seatEntity;
                }).collect(Collectors.toList()));
    }

    @Override
    public void updateSeats(List<SeatDTO> seats) {
        seatRepository.saveAll(seats.stream().map(seat-> modelMapper.map(seat, Seat.class))
                .collect(Collectors.toList()));
    }
}


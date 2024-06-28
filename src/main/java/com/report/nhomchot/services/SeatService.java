package com.report.nhomchot.services;

import com.report.nhomchot.dto.SeatDTO;
import com.report.nhomchot.dto.TheaterDTO;
import com.report.nhomchot.entities.Seat;
import com.report.nhomchot.entities.Theater;
import com.report.nhomchot.repositories.ISeatRepository;
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
public class SeatService{
    @Autowired
    private ISeatRepository seatRepository;
    @Autowired
    private ITheaterRepository theaterRepository;
    @Autowired
    private ITicketRepository ticketRepository;

//    public List<SeatDTO> getSeatsByTheaterIdAndUserId(UUID theaterID, UUID userId) {
//        // Lấy ra các chỗ ngồi của phòng trong lịch đó
//        Theater room = theaterRepository.getById(theaterID);
//        List<Seat> listSeat = seatRepository.getSeatByRoom_Id(room.getId());
//
//        // Lấy ra các vé đã được đặt trong lịch đó rồi map sang các chỗ ngồi
//        List<Seat> occupiedSeats = ticketRepository.findTicketsByTheater_Id(theaterID)
//                .stream().map(ticket -> ticket.getSeat())
//                .collect(Collectors.toList());
//        // Lấy ra các vé người dùng đã đặt trong lịch đó rồi map sang các chỗ ngồi
//        List<Seat> checkedSeats= ticketRepository.findTicketsByUserIdAndStheaterID(userId,theaterID)
//                .stream().map(ticket -> ticket.getSeat())
//                .collect(Collectors.toList());
//        // Map list chỗ ngồi của phòng ở bước 1 sang list dto
//        List<SeatDTO> filteredSeats = listSeat.stream().map(seat -> {
//            SeatDTO seatDTO = new SeatDTO();
//            if(occupiedSeats.stream()
//                    .map(occupiedSeat->occupiedSeat.getId())
//                    .collect(Collectors.toList()).contains(seat.getId())){
//                seatDTO.setIsOccupied(1); // Nếu ghế nào nằm trong list ghế đã được occupied thì set = 1
//            }
//            return seatDTO;
//        }).collect(Collectors.toList());
//        filteredSeats = filteredSeats.stream().map(seat -> {
//            SeatDTO seatDTO = new SeatDTO();
//            if(checkedSeats.stream()
//                    .map(checkedSeat->checkedSeat.getId())
//                    .collect(Collectors.toList()).contains(seat.getId())){
//                seatDTO.setChecked(true); // Nếu ghế nào nằm trong list ghế đã được người dùng đặt thì set = true
//            }
//            return seatDTO;
//        }).collect(Collectors.toList());
//        return  filteredSeats;
//    }

    public List<Seat> getAll(){
        return seatRepository.findAll();
    }

    public List<Seat> getAllSeatByTheater(UUID theaterID) {
        List<Seat> seats = getAll();
        List<Seat> results = new ArrayList<>();

        for (Seat item: seats){
            if(item.getTheater().getId() == theaterID){
                results.add(item);
            }
        }
        return results;
    }

    public List<SeatDTO> getAllSeatDTOByTheater(UUID theaterID) {
        List<Seat> seats = getAll();
        List<SeatDTO> results = new ArrayList<>();

        for (Seat item : seats) {
            if (item.getTheater().getId().equals(theaterID)) {
                SeatDTO seatDTO = convertToDTO(item);
                results.add(seatDTO);
            }
        }
        return results;
    }

    private SeatDTO convertToDTO(Seat seat) {
        SeatDTO seatDTO = new SeatDTO();
        seatDTO.setId(seat.getId());
        seatDTO.setName(seat.getName());
        seatDTO.setActive(seat.isActive());
        seatDTO.setVip(seat.isVip());
        seatDTO.setIsOccupied(0); // hoặc logic khác để xác định ghế có bị chiếm không
        seatDTO.setChecked(false); // hoặc logic khác để xác định ghế có bị chọn không
        seatDTO.setTheater(new TheaterDTO(seat.getTheater().getId(), seat.getTheater().getName()));
        return seatDTO;
    }


//    public void addAllSeat(List<SeatDTO> seats,UUID roomId) {
//        seatRepository.saveAll(seats.stream().map(
//                seat->{
//                    Seat seatEntity = new Seat();
//                    seatEntity.setTheater(new Theater());
//                    seatEntity.getTheater().setId(roomId);
//                    return seatEntity;
//                }).collect(Collectors.toList()));
//    }


//    public void updateSeats(List<SeatDTO> seats) {
//        seatRepository.saveAll(seats.stream().map(seat-> new SeatDTO())
//                .collect(Collectors.toList()));
//    }
}


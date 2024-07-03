package com.report.nhomchot.repositories;

import com.report.nhomchot.entities.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ITicketRepository extends JpaRepository<Ticket, UUID> {
    List<Ticket> findTicketsByShowtime_Id(UUID id);
    List<Ticket> findTicketsByShowtime_IdAndSeat_Id(UUID showTimeId,UUID seatId);
    @Query("SELECT t FROM Ticket t WHERE t.bill.id IN (SELECT b.id FROM Bill b WHERE b.user.id=:userId) ORDER BY t.id DESC")
    List<Ticket> findTicketsByUserId(@Param("userId") UUID userId);
    @Query("SELECT t FROM Ticket t WHERE t.bill.id IN (SELECT b.id FROM Bill b WHERE b.user.id=:userId) "
            + "AND t.showtime.id=:showtimeID"
            + "  ORDER BY t.id DESC")
    List<Ticket> findTicketsByUserIdAndScheduleId(@Param("userId") UUID userId,@Param("showtimeID") UUID showtimeID);
    List<Ticket> findTicketsByBill_Id(UUID billId);

//    @Query("SELECT t FROM Ticket t WHERE t.bill.id IN (SELECT b.id FROM Bill b WHERE b.user.id=:userId) ORDER BY t.id DESC")
}

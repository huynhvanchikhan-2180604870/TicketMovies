package com.report.nhomchot.repositories;

import com.report.nhomchot.dto.SeatDTO;
import com.report.nhomchot.entities.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ISeatRepository extends JpaRepository<Seat, UUID>, JpaSpecificationExecutor<Seat> {
    @Query("select r from Seat r where r.theater.id = ?1")
    List<Seat> getSeatByTheater_Id(UUID theater_id);
}

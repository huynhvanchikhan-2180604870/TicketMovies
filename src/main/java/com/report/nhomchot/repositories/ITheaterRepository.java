package com.report.nhomchot.repositories;

import com.report.nhomchot.entities.Theater;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ITheaterRepository extends JpaRepository<Theater, UUID>, JpaSpecificationExecutor<Theater> {
    @Query("SELECT t FROM Theater t WHERE t.cinema_id = :cinemaId")
    List<Theater> findByCinemaId(@Param("cinemaId") UUID cinemaId);
}

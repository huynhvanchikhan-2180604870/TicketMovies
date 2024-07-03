package com.report.nhomchot.repositories;

import com.report.nhomchot.entities.ShowTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface IShowTimeRepository extends JpaRepository<ShowTime, UUID>, JpaSpecificationExecutor<ShowTime> {
    @Query("select f from ShowTime f where f.theater.id = ?1")
    List<ShowTime> findShowTimesByTheaterID(UUID theater_id);
    @Query("select f from ShowTime f where f.theater.id = ?1 and f.movie.id = ?2")
    List<ShowTime> findSchedulesByTheater_IdAndRoom_Id( UUID theater_id,UUID movieId);
    @Query("select f from ShowTime f where f.theater.id = ?1 and f.movie.id = ?2")
    List<ShowTime> findSchedulesByTheater_IdAndRoom_IdAndMovie_Id(UUID theater_id,UUID movieId);
    @Query("SELECT DISTINCT s.startTime FROM ShowTime s")
    List<LocalDate> getAllStartDateShowTime();
}

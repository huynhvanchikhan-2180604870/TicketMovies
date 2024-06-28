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
    @Query("select t from Theater t where t.cinema.id = :cinemaId")
    List<Theater> findByCinemaId(UUID cinemaId);
}

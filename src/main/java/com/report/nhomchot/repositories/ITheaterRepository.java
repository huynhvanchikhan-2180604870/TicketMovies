package com.report.nhomchot.repositories;

import com.report.nhomchot.entities.Theater;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ITheaterRepository extends JpaRepository<Theater, UUID> {
}

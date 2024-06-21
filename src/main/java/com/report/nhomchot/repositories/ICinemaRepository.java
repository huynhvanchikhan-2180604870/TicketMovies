package com.report.nhomchot.repositories;

import com.report.nhomchot.entities.Cinema;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ICinemaRepository extends JpaRepository<Cinema, UUID>, JpaSpecificationExecutor<Cinema> {
}

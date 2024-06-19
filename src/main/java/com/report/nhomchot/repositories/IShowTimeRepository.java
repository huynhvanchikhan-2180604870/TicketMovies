package com.report.nhomchot.repositories;

import com.report.nhomchot.entities.ShowTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface IShowTimeRepository extends JpaRepository<ShowTime, UUID> {
}

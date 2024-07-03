package com.report.nhomchot.repositories;

import com.report.nhomchot.entities.Bill;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface IBillRepository extends JpaRepository<Bill, UUID> {
}

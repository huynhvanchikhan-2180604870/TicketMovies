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

}

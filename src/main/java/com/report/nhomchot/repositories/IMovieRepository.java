package com.report.nhomchot.repositories;

import com.report.nhomchot.entities.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface IMovieRepository extends JpaRepository<Movie, UUID> {
}

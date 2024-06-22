package com.report.nhomchot.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.report.nhomchot.entities.Movie;

@Repository
public interface IMovieRepository extends JpaRepository<Movie, UUID>, JpaSpecificationExecutor<Movie> {
}

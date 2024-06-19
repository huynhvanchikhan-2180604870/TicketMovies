package com.report.nhomchot.services;

import com.report.nhomchot.entities.Cinema;
import com.report.nhomchot.entities.Movie;
import com.report.nhomchot.repositories.IMovieRepository;
import org.antlr.v4.runtime.misc.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class MovieService {
    @Autowired
    private IMovieRepository movieRepository;

    public MovieService() {
    }

    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

    public Optional<Movie> getMovieById(UUID id) {
        return movieRepository.findById(id);
    }

    public Movie addMovie(Movie movie) {
        return movieRepository.save(movie);
    }

    public Movie updateMovie(@NotNull Movie movie){
        Movie existingMovie = movieRepository.findById(movie.getId())
                .orElseThrow(() -> new IllegalStateException("cinema with ID " +
                        movie.getId() + " does not exist."));
        existingMovie.setTitle(movie.getTitle());
        existingMovie.setDescription(movie.getDescription());
        existingMovie.setDirector(movie.getDirector());
        existingMovie.setRelease_date(movie.getRelease_date());

        return movieRepository.save(existingMovie);
    }
    public void deleteCinema(UUID id){
        if (!movieRepository.existsById(id)) {
            throw new IllegalStateException("Product with ID " + id + " does not exist.");
        }
        movieRepository.deleteById(id);
    }
}

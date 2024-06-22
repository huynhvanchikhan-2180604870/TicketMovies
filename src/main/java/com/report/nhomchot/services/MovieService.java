package com.report.nhomchot.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.antlr.v4.runtime.misc.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.report.nhomchot.entities.Movie;
import com.report.nhomchot.repositories.IMovieRepository;
import com.report.nhomchot.repositories.repo.MovieRepository;

@Service
public class MovieService {
    @Autowired
    private IMovieRepository movieRepository;

    public MovieService() {
    }

    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

    public Page<Movie> getAllMovies(String name, int page, int size, String sortName, String sortDirect){
        Pageable pageable = PageRequest.of(page, size);
        return movieRepository.findAll(MovieRepository.search(name, sortName, sortDirect), pageable);
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
        existingMovie.setDuration(movie.getDuration());
        existingMovie.setRelease_date(movie.getRelease_date());
        existingMovie.setDirector(movie.getDirector());
        existingMovie.setAuthors(movie.getAuthors());
        return movieRepository.save(existingMovie);
    }
    public void deleteCinema(UUID id){
        if (!movieRepository.existsById(id)) {
            throw new IllegalStateException("Product with ID " + id + " does not exist.");
        }
        movieRepository.deleteById(id);
    }
}

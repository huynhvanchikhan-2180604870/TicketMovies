package com.report.nhomchot.controllers.api;

import com.report.nhomchot.entities.Cinema;
import com.report.nhomchot.entities.Movie;
import com.report.nhomchot.models.CinemaModel;
import com.report.nhomchot.models.MovieModel;
import com.report.nhomchot.response.ResponseHandler;
import com.report.nhomchot.services.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/movies")
public class MovieApiController {
    @Autowired
    private MovieService movieService;

    public MovieApiController() {
    }

    @RequestMapping(value = "/get-movies", method = RequestMethod.GET)
    public ResponseEntity<Object> getMovies() {
        return ResponseHandler.responseBuilder("Success", HttpStatus.OK, movieService.getAllMovies());
    }

    @RequestMapping(value = "/get-movie-byid/{id}", method = RequestMethod.GET)
    public ResponseEntity<Object> getMovieById(@PathVariable UUID id) {
        Movie movie = movieService.getMovieById(id).orElseThrow(() -> new IllegalStateException("Movie with ID " +
                id + " does not exist."));
        return ResponseHandler.responseBuilder("Success", HttpStatus.OK, movie);
    }

    @RequestMapping(value = "set-movie", method = RequestMethod.POST)
    public ResponseEntity<Object> setMovie(@RequestBody MovieModel model) {
        Movie movie = new Movie();
        movie.setId(UUID.randomUUID());
        movie.setTitle(model.getTitle());
        movie.setDirector(model.getDirector());
        movie.setDescription(model.getDescription());
        movie.setRelease_date(model.getRelease_date());
        movie.setPoster_url(model.getPoster_url());
        movie.setCategory_id(model.getCategory_id());
        movieService.addMovie(movie);
        return ResponseHandler.responseBuilder("success",
                HttpStatus.OK,
                movieService.getAllMovies()
        );
    }
}

package com.report.nhomchot.controllers.api;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.report.nhomchot.entities.Category;
import com.report.nhomchot.models.CategoryModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.report.nhomchot.entities.Movie;
import com.report.nhomchot.models.FilterOption;
import com.report.nhomchot.models.MovieModel;
import com.report.nhomchot.response.ResponseHandler;
import com.report.nhomchot.services.MovieService;
import com.report.nhomchot.utils.Util;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/movies")
public class MovieApiController {
    @Autowired
    private MovieService movieService;

    public MovieApiController() {
    }

    @RequestMapping(value = "/get-movies", method = RequestMethod.GET)
    public ResponseEntity<Map<String, Object>> getMovies(
            @RequestParam(required = false) String name,
            HttpServletRequest request) {
        Map<String, Object> result = new HashMap<>();
        try {

            FilterOption filterOption = Util.getFilterOption(request);
            int page = filterOption.getiDisplayStart() / filterOption.getiDisplayLength();
            Page<Movie> moviePage = movieService.getAllMovies(name, page, filterOption.getiDisplayLength(),
                    filterOption.getSortName(), filterOption.getsSortDir_0());

            java.util.List<Movie> movies = moviePage.getContent();
            result.put("status", true);
            result.put("message", "");
            result.put("recordsTotal", moviePage.getTotalElements());
            result.put("recordsFiltered", moviePage.getTotalElements());
            result.put("data", movies);

            return ResponseEntity.ok(result);

        } catch (IllegalArgumentException e) {
            result.put("status", false);
            result.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(result);
        } catch (Exception e) {
            result.put("status", false);
            result.put("message", "Internal server error");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);
        }
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
        movie.setDuration(model.getDuration());
        movie.setDescription(model.getDescription());
        movie.setRelease_date(model.getRelease_date());
        movie.setPoster_url(model.getPoster_url());
        movie.setCategory_id(model.getCategory_id());
        movie.setDirector(model.getDirector());
        movie.setAuthors(model.getAuthors());
        movieService.addMovie(movie);

        return ResponseHandler.responseBuilder("success",
                HttpStatus.OK,
                movieService.getAllMovies()
        );
    }

    @RequestMapping(value = "/update-movie/{id}", method = RequestMethod.POST)
    public ResponseEntity<Object> updateCategory(@PathVariable UUID id, @RequestBody MovieModel model,
                                                 BindingResult result) {
        Movie movie = movieService.getMovieById(id).orElseThrow(() -> new IllegalStateException("Cinema with ID " +
                id + " does not exist."));
        movie.setTitle(model.getTitle());
        movie.setDescription(model.getDescription());
        movie.setDuration(model.getDuration());
        movie.setRelease_date(model.getRelease_date());
        movie.setPoster_url(model.getPoster_url());
        movie.setCategory_id(model.getCategory_id());
        movie.setDirector(model.getDirector());
        movie.setAuthors(model.getAuthors());
        movieService.updateMovie(movie);
        return ResponseHandler.responseBuilder("Updated successfully", HttpStatus.OK, model);
    }

    @RequestMapping(value = "/delete-movie/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Object> deleteMovie(@PathVariable UUID id) {
        Movie movie = movieService.getMovieById(id)
                .orElseThrow(() -> new IllegalStateException("Cinema not found"));

        if (movie == null) {
            return ResponseHandler.responseBuilder("Cinema not found", HttpStatus.OK, movieService.getAllMovies());
        }
        movieService.deleteCinema(id);
        return ResponseHandler.responseBuilder("Updated successfully", HttpStatus.OK, movieService.getAllMovies());
    }
}

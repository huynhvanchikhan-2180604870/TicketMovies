package com.report.nhomchot.controllers.api;

import com.report.nhomchot.entities.Movie;
import com.report.nhomchot.entities.Theater;
import com.report.nhomchot.models.MovieModel;
import com.report.nhomchot.models.TheaterModel;
import com.report.nhomchot.response.ResponseHandler;
import com.report.nhomchot.services.TheaterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/theaters")
public class TheaterApiController {
    @Autowired
    private TheaterService theaterService;

    public TheaterApiController() {
    }

    @RequestMapping(value = "/get-theaters", method = RequestMethod.GET)
    public ResponseEntity<Object> getTheater() {
        return ResponseHandler.responseBuilder("Success", HttpStatus.OK, theaterService.getAllTheater());
    }

    @RequestMapping(value = "set-theater", method = RequestMethod.POST)
    public ResponseEntity<Object> setTheater(@RequestBody TheaterModel model) {
        Theater theater = new Theater();
        theater.setId(UUID.randomUUID());
        theater.setCinema_id(model.getCinema_id());
        theater.setSeating_capacity(model.getSeating_capacity());
        theaterService.addTheater(theater);
        return ResponseHandler.responseBuilder("success",
                HttpStatus.OK,
                theaterService.getAllTheater()
        );
    }
}

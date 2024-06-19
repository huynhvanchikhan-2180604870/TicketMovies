package com.report.nhomchot.controllers.api;

import com.report.nhomchot.entities.Cinema;
import com.report.nhomchot.models.CinemaModel;
import com.report.nhomchot.response.ResponseHandler;
import com.report.nhomchot.services.CinemaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/cinemas")
public class CinemaApiController {
    @Autowired
    private CinemaService cinemaService;

    public CinemaApiController(){}

    @RequestMapping(value = "/get-cinemas", method = RequestMethod.GET)
    public ResponseEntity<Object> getCinemas() {
        return ResponseHandler.responseBuilder("Success", HttpStatus.OK, cinemaService.getAllCimema());
    }

    @RequestMapping(value = "/set-cinema")
    public ResponseEntity<Object> setProduct(@RequestBody CinemaModel model) {
        Cinema cinema = new Cinema();
        cinema.setId(UUID.randomUUID());
        cinema.setName(model.getName());
        cinema.setLocation(model.getLocation());
        cinema.setTotal_theaters(model.getTotal_theaters());
        cinemaService.addCinema(cinema);
        return ResponseHandler.responseBuilder("success",
                HttpStatus.OK,
                cinemaService.getAllCimema()
        );
    }


}

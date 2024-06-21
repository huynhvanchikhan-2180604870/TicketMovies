package com.report.nhomchot.controllers.api;

import com.report.nhomchot.entities.Movie;
import com.report.nhomchot.entities.ShowTime;
import com.report.nhomchot.models.MovieModel;
import com.report.nhomchot.models.ShowTimeModel;
import com.report.nhomchot.response.ResponseHandler;
import com.report.nhomchot.services.ShowTimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/show-times")
public class ShowTimeApiController {
    @Autowired
    private ShowTimeService showTimeService;

    public ShowTimeApiController() {
    }

    @RequestMapping(value = "get-showtimes", method = RequestMethod.GET)
    public ResponseEntity<Object> getShowTimes() {
        return ResponseHandler.responseBuilder("Success", HttpStatus.OK, showTimeService.getAllShowTime());
    }

    @RequestMapping(value = "set-showtime", method = RequestMethod.POST)
    public ResponseEntity<Object> setShowTime(@RequestBody ShowTimeModel model) {
        ShowTime showtime = new ShowTime();
        showtime.setId(UUID.randomUUID());
        showtime.setTheater_id(model.getTheater_id());
        showtime.setStartTime(model.getStartTime());
        showtime.setEndTime(model.getEndTime());
        showtime.setPrice(model.getPrice());
        showtime.setMovie_id(model.getMovie_id());
        showTimeService.addShowTime(showtime);
        return ResponseHandler.responseBuilder("success",
                HttpStatus.OK,
                showTimeService.getAllShowTime()
        );
    }
}

package com.report.nhomchot.controllers.api;

import com.report.nhomchot.entities.Movie;
import com.report.nhomchot.entities.ShowTime;
import com.report.nhomchot.entities.Theater;
import com.report.nhomchot.models.FilterOption;
import com.report.nhomchot.models.MovieModel;
import com.report.nhomchot.models.ShowTimeModel;
import com.report.nhomchot.models.TheaterModel;
import com.report.nhomchot.response.ResponseHandler;
import com.report.nhomchot.services.ShowTimeService;
import com.report.nhomchot.services.TheaterService;
import com.report.nhomchot.utils.Util;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/show-times")
public class ShowTimeApiController {
    @Autowired
    private ShowTimeService showTimeService;
    @Autowired
    private TheaterService theaterService;
    public ShowTimeApiController() {
    }

    @RequestMapping(value = "get-showtimes", method = RequestMethod.GET)
    public ResponseEntity<Map<String, Object>> getShowTimes(
            @RequestParam(required = false) String name,
            HttpServletRequest request) {
        Map<String, Object> result = new HashMap<>();
        try {

            FilterOption filterOption = Util.getFilterOption(request);
            int page = filterOption.getiDisplayStart() / filterOption.getiDisplayLength();
            Page<ShowTime> showTimePage = showTimeService.getAllShowTime(name, page, filterOption.getiDisplayLength(),
                    filterOption.getSortName(), filterOption.getsSortDir_0());

            List<ShowTime> showTimes = showTimePage.getContent();
            result.put("status", true);
            result.put("message", "");
            result.put("recordsTotal", showTimePage.getTotalElements());
            result.put("recordsFiltered", showTimePage.getTotalElements());
            result.put("data", showTimes);

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

    @RequestMapping(value = "/update-showtime/{id}", method = RequestMethod.POST)
    public ResponseEntity<Object> updateShowTime(@PathVariable UUID id, @RequestBody ShowTimeModel model,
                                                BindingResult result) {
        ShowTime showTime = showTimeService.getShowTimeById(id).orElseThrow(() -> new IllegalStateException("Theater with ID " +
                id + " does not exist."));
        showTime.setMovie_id(model.getMovie_id());
        showTime.setTheater_id(model.getTheater_id());
        showTime.setStartTime(model.getStartTime());
        showTime.setEndTime(model.getEndTime());
        showTime.setPrice(model.getPrice());
        showTimeService.updateShowTime(showTime);
        return ResponseHandler.responseBuilder("Updated successfully", HttpStatus.OK, model);
    }

    @RequestMapping(value = "/delete-showtime/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Object> deleteShowTime(@PathVariable UUID id) {
        ShowTime showTime = showTimeService.getShowTimeById(id)
                .orElseThrow(() -> new IllegalStateException("Cinema not found"));

        if(showTime == null){
            return ResponseHandler.responseBuilder("Cinema not found", HttpStatus.OK, showTimeService.getAllShowTime());
        }
        showTimeService.deleteCinema(showTime.getId());
        return ResponseHandler.responseBuilder("Updated successfully", HttpStatus.OK, showTimeService.getAllShowTime());
    }

    @GetMapping("/get-all")
    public ResponseEntity<Object> getAllTheaters(@RequestParam(required = false) UUID cinemaId) {
        try {
            List<Theater> theaters;
            if (cinemaId != null) {
                theaters = theaterService.findAllByCinemaId(cinemaId); // Implement this method in your service
            } else {
                theaters = theaterService.getAllTheater(); // General method to get all theaters
            }
            return ResponseHandler.responseBuilder("Theaters fetched successfully", HttpStatus.OK, theaters);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                    "status", false,
                    "message", "Internal server error: " + e.getMessage()
            ));
        }
    }
}

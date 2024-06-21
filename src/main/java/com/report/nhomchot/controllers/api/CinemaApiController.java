package com.report.nhomchot.controllers.api;

import com.report.nhomchot.entities.Cinema;
import com.report.nhomchot.models.CinemaModel;
import com.report.nhomchot.models.FilterOption;
import com.report.nhomchot.response.ResponseHandler;
import com.report.nhomchot.services.CinemaService;
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
@RequestMapping("/api/cinemas")
public class CinemaApiController {
    @Autowired
    private CinemaService cinemaService;

    public CinemaApiController(){}

    @RequestMapping(value = "/get-cinemas", method = RequestMethod.GET)
    public ResponseEntity<Map<String, Object>> getCinemas(
            @RequestParam(required = false) String name,
            HttpServletRequest request) {
        Map<String, Object> result = new HashMap<>();
        try {

            FilterOption filterOption = Util.getFilterOption(request);
            int page = filterOption.getiDisplayStart() / filterOption.getiDisplayLength();
            Page<Cinema> cinemaPage = cinemaService.getAllCinema(name, page, filterOption.getiDisplayLength(),
                    filterOption.getSortName(), filterOption.getsSortDir_0());

            List<Cinema> cinemas = cinemaPage.getContent();
            result.put("status", true);
            result.put("message", "");
            result.put("recordsTotal", cinemaPage.getTotalElements());
            result.put("recordsFiltered", cinemaPage.getTotalElements());
            result.put("data", cinemas);

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

    @RequestMapping(value = "/set-cinema", method = RequestMethod.POST)
    public ResponseEntity<Object> setCinema(@RequestBody CinemaModel model) {
        Cinema cinema = new Cinema();
        cinema.setId(UUID.randomUUID());
        cinema.setName(model.getName());
        cinema.setLocation(model.getLocation());
        cinema.setTotal_theaters(model.getTotal_theaters());
        cinemaService.addCinema(cinema);
        return ResponseHandler.responseBuilder("success",
                HttpStatus.OK,
                cinema
        );
    }

    @RequestMapping(value = "/update-cinema/{id}", method = RequestMethod.POST)
    public ResponseEntity<Object> updateCinema(@PathVariable UUID id, @RequestBody CinemaModel model,
                                                BindingResult result) {
        Cinema cinema = cinemaService.getCinemaById(id).orElseThrow(() -> new IllegalStateException("Cinema with ID " +
                id + " does not exist."));
        cinema.setName(model.getName());
        cinema.setLocation(model.getLocation());
        cinema.setTotal_theaters(model.getTotal_theaters());
        cinemaService.updateCinema(cinema);
        return ResponseHandler.responseBuilder("Updated successfully", HttpStatus.OK, model);
    }

    @RequestMapping(value = "/delete-cinema/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Object> deleteCinema(@PathVariable UUID id) {
        Cinema cinema = cinemaService.getCinemaById(id)
                .orElseThrow(() -> new IllegalStateException("Cinema not found"));

        if(cinema == null){
            return ResponseHandler.responseBuilder("Cinema not found", HttpStatus.OK, cinemaService.getCinemaById(id));
        }
        cinemaService.deleteCinema(id);
        return ResponseHandler.responseBuilder("Updated successfully", HttpStatus.OK, cinemaService.getAllCinema());
    }


}

package com.report.nhomchot.controllers.api;

import com.report.nhomchot.entities.Category;
import com.report.nhomchot.entities.Movie;
import com.report.nhomchot.entities.Theater;
import com.report.nhomchot.models.CategoryModel;
import com.report.nhomchot.models.FilterOption;
import com.report.nhomchot.models.MovieModel;
import com.report.nhomchot.models.TheaterModel;
import com.report.nhomchot.response.ResponseHandler;
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
@RequestMapping("/api/theaters")
public class TheaterApiController {
    @Autowired
    private TheaterService theaterService;

    public TheaterApiController() {
    }

    @RequestMapping(value = "/get-theaters", method = RequestMethod.GET)
    public ResponseEntity<Map<String, Object>> getTheaters(
            @RequestParam(required = false) String name,
            HttpServletRequest request) {
        Map<String, Object> result = new HashMap<>();
        try {

            FilterOption filterOption = Util.getFilterOption(request);
            int page = filterOption.getiDisplayStart() / filterOption.getiDisplayLength();
            Page<Theater> theaterPage = theaterService.getAllTheater(name, page, filterOption.getiDisplayLength(),
                    filterOption.getSortName(), filterOption.getsSortDir_0());

            List<Theater> theaters = theaterPage.getContent();
            result.put("status", true);
            result.put("message", "");
            result.put("recordsTotal", theaterPage.getTotalElements());
            result.put("recordsFiltered", theaterPage.getTotalElements());
            result.put("data", theaters);

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

    @RequestMapping(value = "set-theater", method = RequestMethod.POST)
    public ResponseEntity<Object> setTheater(@RequestBody TheaterModel model) {
        Theater theater = new Theater();
        theater.setId(UUID.randomUUID());
        theater.setName(model.getName());
        theater.setCinema_id(model.getCinema_id());
        theater.setSeating_capacity(model.getSeating_capacity());
        theaterService.addTheater(theater);
        return ResponseHandler.responseBuilder("success",
                HttpStatus.OK,
                theaterService.getAllTheater()
        );
    }

    @RequestMapping(value = "/update-theater/{id}", method = RequestMethod.POST)
    public ResponseEntity<Object> updateTheater(@PathVariable UUID id, @RequestBody TheaterModel model,
                                                 BindingResult result) {
        Theater theater = theaterService.getTheaterById(id).orElseThrow(() -> new IllegalStateException("Theater with ID " +
                id + " does not exist."));
        theater.setCinema_id(model.getCinema_id());
        theater.setName(model.getName());
        theater.setSeating_capacity(model.getSeating_capacity());
        theaterService.updateTheater(theater);
        return ResponseHandler.responseBuilder("Updated successfully", HttpStatus.OK, model);
    }

    @RequestMapping(value = "/delete-theater/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Object> deleteTheater(@PathVariable UUID id) {
        Theater theater = theaterService.getTheaterById(id)
                .orElseThrow(() -> new IllegalStateException("Cinema not found"));

        if(theater == null){
            return ResponseHandler.responseBuilder("Cinema not found", HttpStatus.OK, theaterService.getAllTheater());
        }
        theaterService.deleteTheater(id);
        return ResponseHandler.responseBuilder("Updated successfully", HttpStatus.OK, theaterService.getAllTheater());
    }

    @RequestMapping(value = "/get-all", method = RequestMethod.GET)
    public ResponseEntity<Object> getAllTheater(){
        return ResponseHandler.responseBuilder("Get data success!", HttpStatus.OK, theaterService.getAllTheater());
    }
}

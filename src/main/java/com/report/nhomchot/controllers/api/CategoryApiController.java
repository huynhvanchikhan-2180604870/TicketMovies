package com.report.nhomchot.controllers.api;

import com.report.nhomchot.entities.Category;
import com.report.nhomchot.entities.Cinema;
import com.report.nhomchot.models.CategoryModel;
import com.report.nhomchot.models.CinemaModel;
import com.report.nhomchot.models.FilterOption;
import com.report.nhomchot.response.ResponseHandler;
import com.report.nhomchot.services.CategoryService;
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
@RequestMapping("/api/categories")
public class CategoryApiController {
    @Autowired
    private CategoryService categoryService;

    public CategoryApiController() {
    }

    @RequestMapping(value = "/get-categories", method = RequestMethod.GET)
    public ResponseEntity<Map<String, Object>> getCinemas(
            @RequestParam(required = false) String name,
            HttpServletRequest request) {
        Map<String, Object> result = new HashMap<>();
        try {

            FilterOption filterOption = Util.getFilterOption(request);
            int page = filterOption.getiDisplayStart() / filterOption.getiDisplayLength();
            Page<Category> categoryPage = categoryService.getAllCategory(name, page, filterOption.getiDisplayLength(),
                    filterOption.getSortName(), filterOption.getsSortDir_0());

            List<Category> categories = categoryPage.getContent();
            result.put("status", true);
            result.put("message", "");
            result.put("recordsTotal", categoryPage.getTotalElements());
            result.put("recordsFiltered", categoryPage.getTotalElements());
            result.put("data", categories);

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

    @RequestMapping(value = "/set-category", method = RequestMethod.POST)
    public ResponseEntity<Object> setCategory(@RequestBody CategoryModel model){
        Category category = new Category();
        category.setId(UUID.randomUUID());
        category.setAge_accept(model.getAge_accept());
        category.setName(model.getName());
        categoryService.addCategory(category);
        return ResponseHandler.responseBuilder("Success", HttpStatus.OK, categoryService.getAllCategory());
    }

    @RequestMapping(value = "/update-category/{id}", method = RequestMethod.POST)
    public ResponseEntity<Object> updateCategory(@PathVariable UUID id, @RequestBody CategoryModel model,
                                               BindingResult result) {
        Category category = categoryService.getCategoryById(id).orElseThrow(() -> new IllegalStateException("Cinema with ID " +
                id + " does not exist."));
        category.setAge_accept(model.getAge_accept());
        category.setName(model.getName());
        categoryService.updateCategory(category);
        return ResponseHandler.responseBuilder("Updated successfully", HttpStatus.OK, model);
    }

    @RequestMapping(value = "/delete-category/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Object> deleteCategory(@PathVariable UUID id) {
        Category category = categoryService.getCategoryById(id)
                .orElseThrow(() -> new IllegalStateException("Cinema not found"));

        if(category == null){
            return ResponseHandler.responseBuilder("Cinema not found", HttpStatus.OK, categoryService.getAllCategory());
        }
        categoryService.deleteCategory(id);
        return ResponseHandler.responseBuilder("Updated successfully", HttpStatus.OK, categoryService.getAllCategory());
    }
}

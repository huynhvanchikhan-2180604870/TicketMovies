package com.report.nhomchot.controllers.api;

import com.report.nhomchot.entities.Category;
import com.report.nhomchot.models.CategoryModel;
import com.report.nhomchot.response.ResponseHandler;
import com.report.nhomchot.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/categories")
public class CategoryApiController {
    @Autowired
    private CategoryService categoryService;

    public CategoryApiController() {
    }

    @RequestMapping(value = "/get-categories", method = RequestMethod.GET)
    public ResponseEntity<Object> getCategory() {
        return ResponseHandler.responseBuilder("Success", HttpStatus.OK, categoryService.getAllCategory());
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
}

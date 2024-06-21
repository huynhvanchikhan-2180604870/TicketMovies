package com.report.nhomchot.services;

import com.report.nhomchot.entities.Category;
import com.report.nhomchot.entities.Cinema;
import com.report.nhomchot.repositories.ICategoryRepository;
import com.report.nhomchot.repositories.repo.CategoryRepository;
import com.report.nhomchot.repositories.repo.CinemaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CategoryService {
    @Autowired
    private ICategoryRepository categoryRepository;

    public CategoryService(){}

    public List<Category> getAllCategory(){
        return categoryRepository.findAll();
    }

    public Page<Category> getAllCategory(String name, int page, int size, String sortName, String sortDirect){
        Pageable pageable = PageRequest.of(page, size);
        return categoryRepository.findAll(CategoryRepository.search(name, sortName, sortDirect), pageable);
    }

    public Optional<Category> getCategoryById(UUID id){
        return categoryRepository.findById(id);
    }


    public Category addCategory(Category category){
        return categoryRepository.save(category);
    }

    public Category updateCategory(Category category){
        Category existingCategory = categoryRepository.findById(category.getId())
                .orElseThrow(() -> new IllegalStateException("Category with ID " +
                        category.getId() + " does not exist."));
        existingCategory.setAge_accept(category.getAge_accept());
        existingCategory.setName(category.getName());
        return categoryRepository.save(existingCategory);
    }

    public void deleteCategory(UUID id){
        if(!categoryRepository.existsById(id)){
            throw new IllegalStateException("Category is null");
        }
        categoryRepository.deleteById(id);
    }
}

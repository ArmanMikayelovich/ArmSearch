package project.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;

import project.exception.ResourceNotFoundException;
import project.model.CategoryEntity;
import project.model.ProductEntity;
import project.repository.CategoryRepository;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class CategoryController {
    @Autowired
    CategoryRepository categoryRepository;

    // Get All Categories
    @GetMapping("/categories")
    public List<CategoryEntity> getAllCategories() {

        return categoryRepository.findAll();
    }

    // Create a new Category //TODO this must be accesible only for admins and delete
    @PostMapping("/categories")
    public CategoryEntity createCategory(@Valid @RequestBody CategoryEntity categoryEntity) {
        return categoryRepository.save(categoryEntity);
    }

    // Get a Single category
    @GetMapping("/categories/{id}")
    public CategoryEntity getCategoryById(@PathVariable(value = "id") Integer categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", categoryId));
    }

       // Delete a Category
    @DeleteMapping("/categories/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable(value = "id") Integer categoryId) {
        CategoryEntity category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", categoryId));
        categoryRepository.delete(category);

        return ResponseEntity.ok().build();
    }
}

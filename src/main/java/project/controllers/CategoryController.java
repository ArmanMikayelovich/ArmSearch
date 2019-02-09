package project.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.DeleteMapping;

import org.springframework.web.servlet.ModelAndView;
import project.dto.CategoryDto;
import project.exception.ResourceNotFoundException;
import project.model.Category;
import project.model.CategoryGroup;
import project.repository.CategoryGroupRepository;
import project.repository.CategoryRepository;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CategoryController {
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    CategoryGroupRepository categoryGroupRepository;
//    @Autowired
//    SessionFactory sessionFactory;

    // Get All Categories
    @GetMapping("/categories")
    public ModelAndView getAllCategories() {
        ModelAndView modelAndView = new ModelAndView("addCategory");
        modelAndView.addObject("groups", categoryGroupRepository.findAll());

        return modelAndView;
    }

    // Create a new Category //TODO this must be accesible only for admins and delete
    @PostMapping("/categories") //TODO ARMAN try to do true...
    public Category createCategory(CategoryDto categoryDto) {

        List<CategoryGroup> list = categoryGroupRepository.findByName(categoryDto.getGroup());
        CategoryGroup categoryGroup = list.get(0);
        Category category = new Category(categoryDto.getName(), categoryGroup);
        return categoryRepository.save(category);
    }

    // Get a Single category
    @GetMapping("/categories/{id}")
    public Category getCategoryById(@PathVariable(value = "id") Integer categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", categoryId));
    }//TODO ARO category veradarcnelu poxaren piti front uxarkes dra miji bor Item ner@


       // Delete a Category
    @DeleteMapping("/categories/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable(value = "id") Integer categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", categoryId));
        categoryRepository.delete(category);

        return ResponseEntity.ok().build();
    }
}

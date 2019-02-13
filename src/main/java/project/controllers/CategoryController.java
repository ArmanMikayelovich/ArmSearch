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
import project.service.CategoryService;

import java.util.List;

//TODO Arman u Ani es class@ dzevapoxel hamadzayn telegrami meji im grac 12.02.2019 00:34 /amboxjutyamb/

@RestController
@RequestMapping
public class CategoryController {
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    CategoryGroupRepository categoryGroupRepository;

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }
//
//    // Get All Categories
//    @GetMapping("/categories")
//    public ModelAndView getAllCategories() {
//        ModelAndView modelAndView = new ModelAndView("addCategory");
//        modelAndView.addObject("groups", categoryGroupRepository.findAll());
//
//        return modelAndView;
//    }

    // Create a new Category //TODO this must be accesible only for admins and delete
    @PostMapping("/categories/add") //TODO ARMAN try to do true...
    public Category createCategory(CategoryDto categoryDto) {

        Category category;
        category = categoryService.createCategory(categoryDto);

        return categoryRepository.save(category);
    }

    // Get a Single category
    @GetMapping("/categories/{id}")
    public Category getCategoryById(@PathVariable(value = "id") Integer categoryId) {
        return categoryService.findById(categoryId);
        //TODO ARO category veradarcnelu poxaren piti front uxarkes dra miji bor Item ner@
//        return categoryService.findById(categoryId).getItemList(  );
    }



       // Delete a Category
    @DeleteMapping("/categories/delete/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable(value = "id") Integer categoryId) {

        categoryService.deleteCategory(categoryId);
        return ResponseEntity.ok().build();
    }
}

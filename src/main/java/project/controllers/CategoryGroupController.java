package project.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.ModelAndView;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.DeleteMapping;


import project.model.CategoryEntity;
import project.repository.CategoryRepository;
import project.service.CategoryService;

// TODO tes CategoryController classi TODO-n

@RestController
@RequestMapping
public class CategoryGroupController {

   private final CategoryRepository categoryRepository;

    private final CategoryService categoryService;

    public CategoryGroupController(CategoryRepository categoryRepository, CategoryService categoryService) {
        this.categoryRepository = categoryRepository;
        this.categoryService = categoryService;
    }

    // Get All CategoryGroups
    @GetMapping("/categoryGroups")
    public ModelAndView getAllCategoryGroups() {
        ModelAndView modelAndView = new ModelAndView("addCategoryGroup");
             modelAndView.addObject("groups", categoryRepository.findAll());
        return modelAndView;
    }

    // Create a new CategoryEntity //TODO this must be accesible only for admins and delete
    @PostMapping("/addCategoryGroup")
    public CategoryEntity createCategoryGroup(CategoryEntity categoryEntity) {
        categoryService.createCategory(categoryEntity);

        return categoryRepository.save(categoryEntity);
    }



    // Delete a CategoryEntity
    @DeleteMapping("/categoryGroups/delete/{id}")
    public ResponseEntity<?> deleteCategoryGroup(@PathVariable(value = "id") Integer categoryGroupId) {
       categoryService.deleteCategory(categoryGroupId);
        return ResponseEntity.ok().build();
    }
}

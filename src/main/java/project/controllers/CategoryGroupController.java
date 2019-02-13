package project.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.ModelAndView;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.DeleteMapping;


import project.exception.ResourceNotFoundException;
import project.model.CategoryGroup;
import project.repository.CategoryGroupRepository;
import project.service.CategoryGroupService;

// TODO tes CategoryController classi TODO-n

@RestController
@RequestMapping
public class CategoryGroupController {

   private final CategoryGroupRepository categoryGroupRepository;

    private final CategoryGroupService categoryGroupService;

    public CategoryGroupController(CategoryGroupRepository categoryGroupRepository, CategoryGroupService categoryGroupService) {
        this.categoryGroupRepository = categoryGroupRepository;
        this.categoryGroupService = categoryGroupService;
    }

    // Get All CategoryGroups
    @GetMapping("/categoryGroups")
    public ModelAndView getAllCategoryGroups() {
        ModelAndView modelAndView = new ModelAndView("addCategoryGroup");
             modelAndView.addObject("groups", categoryGroupRepository.findAll());
        return modelAndView;
    }

    // Create a new CategoryGroup //TODO this must be accesible only for admins and delete
    @PostMapping("/categoryGroups/add")
    public CategoryGroup createCategoryGroup(CategoryGroup categoryGroup) {
        categoryGroupService.createCategoryGroup(categoryGroup);

        return categoryGroupRepository.save(categoryGroup);
    }

//    // Get a Single categoryGroup//TODO or get all categories of this group
//    @GetMapping("/categoryGroups/{id}")
//    public CategoryGroup getCategoryGroupById(@PathVariable(value = "id") Integer categoryGroupId) {
//        return categoryGroupService.findById(categoryGroupId);
//
//    }

    // Delete a CategoryGroup
    @DeleteMapping("/categoryGroups/delete/{id}")
    public ResponseEntity<?> deleteCategoryGroup(@PathVariable(value = "id") Integer categoryGroupId) {
       categoryGroupService.deleteCategoryGroup(categoryGroupId);
        return ResponseEntity.ok().build();
    }
}

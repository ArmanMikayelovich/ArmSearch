package project.controllers;


import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.servlet.ModelAndView;
import project.dto.CategoryDto;
import project.model.Category;
import project.model.CategoryGroup;
import project.model.User;
import project.repository.CategoryGroupRepository;

import project.repository.CategoryRepository;
import project.service.CategoryGroupService;
import project.service.CategoryService;
import project.service.UserService;

import java.util.List;

@RestController
@RequestMapping
public class CategoryController {
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    CategoryGroupRepository categoryGroupRepository;

    private final CategoryService categoryService;
    private final UserService userService;

    private final CategoryGroupService categoryGroupService;
 public CategoryController(CategoryService categoryService, UserService userService, CategoryGroupService categoryGroupService) {
        this.categoryService = categoryService;
        this.userService = userService;
        this.categoryGroupService = categoryGroupService;
    }

    // Get All Categories
    @GetMapping("/addCategory")
    public ModelAndView getAllCategories() {
        ModelAndView modelAndView = new ModelAndView("addCategory");
        modelAndView.addObject("groups", categoryGroupRepository.findAll());

        return modelAndView;
    }

    // Create a new Category //TODO this must be accesible only for admins and delete
    @PostMapping(value = "/addCategory")

    public Category createCategory(CategoryDto categoryDto) {

        Category category;
        category = categoryService.createCategory(categoryDto);

        return categoryRepository.save(category);
    }

    // Get a Single category
    @GetMapping("/categories/{id}")
    public ModelAndView getCategoryById(@PathVariable(value = "id") Integer categoryId) {
        ModelAndView view = new ModelAndView("Category");
        categoryService.getCategoriesWithTheirGroups(view);
        view.addObject("itemList", categoryService.findById(categoryId).getItemList());
        try {
            User.User auth = userService.getAuthenticatedUser();
            view.addObject("user", auth);

        } catch (Exception e) {
            User.User adminpage = userService.getUserById(1);
            view.addObject("user", adminpage);

        }
        return view;
    }


    // Delete a Category //TODO @PostMapping or @GetMapping for easy deleting from front-end...
    @PostMapping("/deleteCategory/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable(value = "id") Integer categoryId) {

        categoryService.deleteCategory(categoryId);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/getCategoryList")
    public List<Category> getCategoryList(@RequestParam(value = "id") Integer id) {
        CategoryGroup group = categoryGroupRepository.findById(id).get();
        return group.getCategories();
    }

}

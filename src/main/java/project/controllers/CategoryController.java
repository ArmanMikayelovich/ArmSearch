package project.controllers;


import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

import org.springframework.web.servlet.ModelAndView;
import project.dto.CategoryDto;
import project.model.CategoryEntity;
import project.model.SubCategoryEntity;
import project.model.UserEntity;
import project.repository.CategoryRepository;

import project.repository.SubCategoryRepository;
import project.service.CategoryService;
import project.service.SubCategoryService;
import project.service.UserService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping
public class CategoryController {
    @Autowired
    SubCategoryRepository subCategoryRepository;
    @Autowired
    CategoryRepository categoryRepository;

    private final SubCategoryService subCategoryService;
    private final UserService userService;

    private final CategoryService categoryService;
 public CategoryController(SubCategoryService subCategoryService, UserService userService, CategoryService categoryService) {
        this.subCategoryService = subCategoryService;
        this.userService = userService;
        this.categoryService = categoryService;
    }


    // Create a new SubCategoryEntity //TODO this must be accesible only for admins and delete
    @PostMapping(value = "/addSubCategory")
    public void createCategory(CategoryDto categoryDto, HttpServletResponse response) {


          subCategoryService.createCategory(categoryDto);

        try {
            response.sendRedirect("/admin/categories");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Get a Single subCategoryEntity
    @GetMapping("/categories/{id}")
    public ModelAndView getCategoryById(@PathVariable(value = "id") Integer categoryId) {
        ModelAndView view = new ModelAndView("Home");
        subCategoryService.getCategoriesWithTheirGroups(view);
        view.addObject("itemList", subCategoryService.findById(categoryId).getItemEntityList());
        try {
            UserEntity auth = userService.getAuthenticatedUser();
            view.addObject("user", auth);

        } catch (Exception e) {
            UserEntity adminpage = userService.getUserById(1);
            view.addObject("userEntity", adminpage);

        }
        return view;
    }


    @PostMapping("/deleteSubCategory/{id}")
    public void deleteCategory(@PathVariable(value = "id") Integer categoryId, HttpServletResponse response) {

        subCategoryService.deleteCategory(categoryId);
        try {
            response.sendRedirect("/admin/categories");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}

package project.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import project.repository.CategoryRepository;
import project.service.SubCategoryService;
import project.service.ImageService;
import project.service.ItemService;
import project.service.UserService;

@RestController
@RequestMapping("/admin")
public class AdminPanelController {
    private final UserService userService;
    private final ItemService itemService;
    private final SubCategoryService subCategoryService;
    private final CategoryRepository categoryRepository;

    private final ImageService imageService;

    public AdminPanelController(UserService userService, ItemService itemService, SubCategoryService subCategoryService, CategoryRepository categoryRepository, ImageService imageService) {
        this.userService = userService;
        this.itemService = itemService;
        this.subCategoryService = subCategoryService;
        this.categoryRepository = categoryRepository;

        this.imageService = imageService;
    }

    @GetMapping(value = {"/main","/"})
    public ModelAndView getAdminPanel() {


        ModelAndView modelAndView = new ModelAndView("Admin");

        modelAndView.addObject("usersCount", userService.getCountOfUsers());
        modelAndView.addObject("categoriesCount", subCategoryService.getCountofSubCategories());
        modelAndView.addObject("itemsCount", itemService.getCountOfItems());
        modelAndView.addObject("imgCount", imageService.getCountOfImages());

        return modelAndView;
    }
    @GetMapping("/users")
    public ModelAndView getUsersPanel() {
        ModelAndView modelAndView = new ModelAndView("Admin_Users");
        modelAndView.addObject("userList", userService.getAllUsers());
        return modelAndView;
    }

    @GetMapping("/items")
    public ModelAndView getItemsPanel() {
        ModelAndView modelAndView = new ModelAndView("Admin_Items");
        modelAndView.addObject("itemList", itemService.getAllItems());
        return modelAndView;
    }

    @GetMapping("/categories")
    public ModelAndView getCategoriesPanel() {
        ModelAndView modelAndView = new ModelAndView("Admin_Categories");
        modelAndView.addObject("categoryList", subCategoryService.getAllSubCategories());
        modelAndView.addObject("groups", categoryRepository.findAll());

        return modelAndView;
    }

}

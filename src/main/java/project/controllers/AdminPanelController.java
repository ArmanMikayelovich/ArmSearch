package project.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import project.service.CategoryService;
import project.service.ImageService;
import project.service.ItemService;
import project.service.UserService;

@RestController
@RequestMapping("/admin")
public class AdminPanelController {
    private final UserService userService;
    private final ItemService itemService;
    private final CategoryService categoryService;
    private final ImageService imageService;

    public AdminPanelController(UserService userService, ItemService itemService, CategoryService categoryService, ImageService imageService) {
        this.userService = userService;
        this.itemService = itemService;
        this.categoryService = categoryService;

        this.imageService = imageService;
    }

    @GetMapping("/main")
    public ModelAndView getAdminPanel() {


        ModelAndView modelAndView = new ModelAndView("Admin");

        modelAndView.addObject("usersCount", userService.getCountOfUsers());
        modelAndView.addObject("categoriesCount", categoryService.getCountofCategories());
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

}

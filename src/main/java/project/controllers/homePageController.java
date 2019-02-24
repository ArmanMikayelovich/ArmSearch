package project.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import project.model.User;
import project.service.CategoryGroupService;
import project.service.CategoryService;
import project.service.ItemService;
import project.service.UserService;

@RestController
@RequestMapping
public class homePageController {
    private final CategoryGroupService categoryGroupService;
    private final ItemService itemService;
    private final CategoryService categoryService;
    private final UserService userService;

    public homePageController(CategoryGroupService categoryGroupService, ItemService itemService, CategoryService categoryService, UserService userService) {
        this.categoryGroupService = categoryGroupService;
        this.itemService = itemService;
        this.categoryService = categoryService;
        this.userService = userService;
    }

    @GetMapping(value = {"/", "/home"})
    public ModelAndView getHomePage(@RequestParam(value = "categoryId",required = false) Integer categoryId) {
        ModelAndView view = new ModelAndView("Home");
        view = categoryService.getCategoriesWithTheirGroups(view);
        if (categoryId == null) {
            view.addObject("randomItemList", itemService.getRamdomItems(555));
            try {
                User auth = userService.getAuthenticatedUser();
                view.addObject("user", auth);

            } catch (Exception e) {
//                User adminpage = userService.getUserById(1);
                view.addObject("user", new User());


            }
        }
        return view;
    }

}

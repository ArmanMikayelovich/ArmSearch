package project.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import project.model.User;
import project.service.CategoryGroupService;
import project.service.ItemService;
import project.service.UserService;

@RestController
@RequestMapping
public class homePageController {
    private final CategoryGroupService categoryGroupService;
    private final ItemService itemService;

    private final UserService userService;

    public homePageController(CategoryGroupService categoryGroupService, ItemService itemService, UserService userService) {
        this.categoryGroupService = categoryGroupService;
        this.itemService = itemService;
        this.userService = userService;
    }

    @GetMapping(value = {"/", "/home"})
    public ModelAndView getHomePage() {
        ModelAndView view = new ModelAndView("Home");
        view.addObject("electronicsGroup", categoryGroupService.findByName("Electronics"));
        view.addObject("realEstateGroup", categoryGroupService.findByName("Real Estate"));
        view.addObject("vehiclesGroup", categoryGroupService.findByName("Vehicle"));
        view.addObject("servicesGroup", categoryGroupService.findByName("Services"));
        view.addObject("jobsGroup", categoryGroupService.findByName("Jobs"));
        view.addObject("fashionGroup", categoryGroupService.findByName("Fashion"));
        view.addObject("toolsAndMaterialsGroup", categoryGroupService.findByName("Tools and Materials"));
        view.addObject("householdGroup", categoryGroupService.findByName("Household"));
        view.addObject("forKidsGroup", categoryGroupService.findByName("For Kids"));
        view.addObject("cultureAndHobbyGroup", categoryGroupService.findByName("Culture and Hobby"));
        view.addObject("appliancesGroup", categoryGroupService.findByName("Appliances"));
        view.addObject("everythinkElseGroup", categoryGroupService.findByName("Everythink Else"));

        try{
            User auth = userService.getAuthenticatedUser();
            view.addObject("user", auth);

        } catch (Exception e) {
            User adminpage = userService.getUserById(1);
            view.addObject("user", adminpage);

        }
        return view;
    }

}

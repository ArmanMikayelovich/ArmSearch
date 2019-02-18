package project.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import project.service.CategoryGroupService;


public class homePageController {
    private final CategoryGroupService categoryGroupService;

    public homePageController(CategoryGroupService categoryGroupService) {
        this.categoryGroupService = categoryGroupService;
    }

    @GetMapping(value = {"/", "/home"})
    public ModelAndView getHomePage() {
        ModelAndView view = new ModelAndView("Home");
        view.addObject("categoryGroup1",categoryGroupService.findByName("Electronics"))
    }

}

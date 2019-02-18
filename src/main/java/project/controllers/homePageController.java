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
        view.addObject("electronicsGroup",categoryGroupService.findByName("Electronics"))
        view.addObject("realEstateGroup",categoryGroupService.findByName("Electronics"))
        view.addObject("vehiclesGroup",categoryGroupService.findByName("Electronics"))
        view.addObject("servicesGroup",categoryGroupService.findByName("Electronics"))
        view.addObject("jobsGroup",categoryGroupService.findByName("Electronics"))
        view.addObject("fashionGroup",categoryGroupService.findByName("Electronics"))
        view.addObject("toolsAndMaterialsGroup",categoryGroupService.findByName("Electronics"))
        view.addObject("HouseholdGroup",categoryGroupService.findByName("Electronics"))
        view.addObject("forKidsGroup",categoryGroupService.findByName("Electronics"))
        view.addObject("Group",categoryGroupService.findByName("Electronics"))
    }

}

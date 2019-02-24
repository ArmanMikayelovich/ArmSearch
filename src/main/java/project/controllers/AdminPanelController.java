package project.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping
public class AdminPanelController {
    @GetMapping("/admin")
    public ModelAndView getAdminPanel() {
        ModelAndView modelAndView = new ModelAndView("Admin");

        modelAndView.addObject("usersCount", String.valueOf(777));
        modelAndView.addObject("categoriesCount", String.valueOf(666));
        modelAndView.addObject("itemsCount", String.valueOf(456));
        return modelAndView;
    }
}

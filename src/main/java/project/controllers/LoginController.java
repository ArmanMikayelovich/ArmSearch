package project.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import project.service.UserService;

import java.security.Principal;

@RestController
@RequestMapping
public class LoginController {
    @Autowired
    AuthenticationProvider provider;
    private final UserService userService;

    public LoginController(UserService userService)
    {
        this.userService = userService;
    }


    @GetMapping("/login")
    public ModelAndView getLoginPage(Principal authentication)  {
        ModelAndView modelAndView = new ModelAndView("login");
        try {
            if (authentication != null) {
                return new ModelAndView("redirect:/users/" + userService.getAuthenticatedUser().getId());
            }
        } catch (Exception e) {

        }
        return modelAndView;
    }


}

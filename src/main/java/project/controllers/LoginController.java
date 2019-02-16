package project.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import project.dto.UserAuth;
import project.model.User;
import project.service.UserService;

import java.util.HashSet;

@RestController
@RequestMapping
public class LoginController {
    @Autowired
    AuthenticationProvider provider;
    private final UserService userService;

    public LoginController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/login")
    public ModelAndView getLoginPage() {
        ModelAndView modelAndView = new ModelAndView("login");
        return modelAndView;
    }


    @GetMapping("/valod")//only for test... this is only for Authenticated users XD
    public ModelAndView valod() {
        ModelAndView m = new ModelAndView("registration");
        return m;
    }


}

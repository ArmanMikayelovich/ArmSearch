package project.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.servlet.ModelAndView;

import project.dto.UserDto;
import project.exception.ResourceNotFoundException;
import project.model.User;
import project.repository.UserRepository;
import project.service.ItemService;
import project.service.UserService;

import javax.jws.WebParam;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.io.IOException;
import java.security.Principal;

@RestController
@RequestMapping()
public class UserController {

    @Autowired
    UserRepository userRepository;
    @Autowired
    UserService userService;
    @Autowired
    ItemService itemService;
    // Get All Users
    @GetMapping("/users")
    public ModelAndView getAllUsers() {
        ModelAndView modelAndView = new ModelAndView("registerUser");
        modelAndView.addObject("users", userService.findAll());
        return modelAndView;
    }

    @GetMapping("/registration")
    public ModelAndView getRegistrationPage(Authentication authentication){
        ModelAndView modelAndView = new ModelAndView("registration");
        if (authentication!=null) {
//            return new ModelAndView("redirect:/users/"+userService.getAuthenticatedUser().getId());
        }
        return modelAndView;
    }



    @GetMapping("/users/{id}")
    public ModelAndView getUserPage(@PathVariable("id") Integer id) {
        ModelAndView modelAndView = new ModelAndView("user");
        User user = userService.getUserById(id);
        modelAndView.addObject("user", user);
        modelAndView.addObject("items", user.getItemList());
        modelAndView.addObject("isCreator",
                user.getItemList().isEmpty() ? false : itemService.isCreator(user.getItemList().get(0)));

        return modelAndView;
    }



    @PostMapping("/registration")
    void addNewUser(User user,HttpServletResponse response) {
        userService.saveUser(user);

        try {
              response.sendRedirect("/");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }






    @GetMapping("/updateUser")
    public ModelAndView getUpdateUserPage() {
        ModelAndView modelAndView = new ModelAndView("EditUser");
        return modelAndView;
    }

    @PostMapping("/updateUser")
    /**
        * vercnel useri tvyalnery u dnel useri mej
     */
    public void updateUser(@Valid UserDto user,HttpServletResponse response) {
        userService.updateUser(user);
        try {
            response.sendRedirect("/users/" + userService.getAuthenticatedUser().getId());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @PostMapping("/changePassword")
    public void changePassword(String oldPassword, String newPassword, HttpServletResponse response) {
        userService.changePassword(oldPassword, newPassword);
        try {
            response.sendRedirect("/updateUser");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Delete a User
    @PostMapping("/deleteUser")
    public void deleteUser(String password,HttpServletResponse response)  {
        try {
            User user = userService.getAuthenticatedUser();
            userService.deleteUser(password);
            response.sendRedirect("/logout");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @PostMapping("/deleteUserFromAdminPanel")
    public void deleteUserFromAdminPanel(Integer id,HttpServletResponse response) throws Exception {
        User admin = userService.getAuthenticatedUser();
        if (admin.getRoleName().equals("ADMIN")) {
            userService.deleteUserFromAdminPanel(id);
            try {
                response.sendRedirect("/admin/users");
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else {
            try {
                response.sendRedirect("/404");
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

}
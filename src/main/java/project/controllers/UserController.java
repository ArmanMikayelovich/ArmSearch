package project.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.servlet.ModelAndView;

import project.exception.ResourceNotFoundException;
import project.model.User;
import project.service.UserService;

import javax.jws.WebParam;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;

@RestController
@RequestMapping
public class UserController {

    private final UserService userService;

    public UserController( UserService userService) {
        this.userService = userService;
    }



    // Get All Users//todo only for admin...
    @GetMapping("/users")
    public ModelAndView getAllUsers() {
        ModelAndView modelAndView = new ModelAndView("registerUser");
            modelAndView.addObject("users",userService.findAll());
        return modelAndView;
    }
    /////////
//    @PostMapping(path = "/users",  consumes = { MediaType.APPLICATION_JSON_VALUE },
//            headers = "application/x-www-form-urlencoded;charset=UTF-8",
//            produces = "application/json")
    @GetMapping("/registration")
    public ModelAndView getRegistrationPage(){
        ModelAndView modelAndView = new ModelAndView("registration");
        return modelAndView;
    }


    @GetMapping("/users/{id}")
    public ModelAndView getUserPage(@PathVariable("id") Integer id) {
        ModelAndView modelAndView = new ModelAndView("user");
        User user = userService.getUserById(id);
        modelAndView.addObject("user", user);
        modelAndView.addObject("items", user.getItemList());
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
    public User updateUser(@Valid User user) {
        userService.updateUser(user);
        return user;
    }

    @PostMapping("/changePassword")
    public void changePassword(String oldPassword, String newPassword, HttpServletResponse response) {
        userService.changePassword(oldPassword, newPassword);
        try {
            response.sendRedirect("");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Delete a User
    @PostMapping("/deleteUser")
    public ResponseEntity<?> deleteUser(String password)  {
        User user = userService.getAuthenticatedUser();
        userService.deleteUser(password);
        return ResponseEntity.ok().build();
    }

}
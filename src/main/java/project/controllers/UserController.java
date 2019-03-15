package project.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.servlet.ModelAndView;

import project.dto.UserDto;
import project.model.UserEntity;
import project.repository.UserRepository;
import project.service.ItemService;
import project.service.UserService;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;

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
            try {
                return new ModelAndView("redirect:/users/"+userService.getAuthenticatedUser().getId());
            } catch (Exception e) {
                return new ModelAndView("redirect://home");
            }
        }
        return modelAndView;
    }



    @GetMapping("/users/{id}")
    public ModelAndView getUserPage(@PathVariable("id") Integer id) {
        ModelAndView modelAndView = new ModelAndView("user");
        UserEntity userEntity = userService.getUserById(id);
        modelAndView.addObject("userEntity", userEntity);
        modelAndView.addObject("items", userEntity.getItemEntityList());
        modelAndView.addObject("isCreator",
                userEntity.getItemEntityList().isEmpty() ? false : itemService.isCreator(userEntity.getItemEntityList().get(0)));

        return modelAndView;
    }



    @PostMapping("/registration")
    void addNewUser(UserEntity userEntity, HttpServletResponse response) {
        userService.saveUser(userEntity);

        try {
              response.sendRedirect("/");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }






    @GetMapping("/updateUser")
    public ModelAndView getUpdateUserPage() {
        ModelAndView modelAndView = new ModelAndView("UpdateUser");
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
    public ModelAndView changePassword(String oldPassword, String newPassword, HttpServletResponse response) throws Exception {
        if (userService.changePassword(oldPassword, newPassword)) {
          return  new ModelAndView("RedirectPage")
                    .addObject("redirectUrl",
                            "/users/" + userService.getAuthenticatedUser().getId())
                    .addObject("redirectMessage", "Your password successfully changed");
        }
        else {
          return  new ModelAndView("RedirectPage")
                    .addObject("redirectUrl",
                            "/users/" + userService.getAuthenticatedUser().getId())
                    .addObject("redirectMessage", "An error has occurred. You will be redireced");
        }


    }

    // Delete a UserEntity
    @PostMapping("/deleteUser")
    public void deleteUser(String password,HttpServletResponse response)  {
        try {
            UserEntity userEntity = userService.getAuthenticatedUser();
            userService.deleteUser(password);
            response.sendRedirect("/logout");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @PostMapping("/deleteUserFromAdminPanel")
    public void deleteUserFromAdminPanel(Integer id,HttpServletResponse response) throws Exception {
        UserEntity admin = userService.getAuthenticatedUser();
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
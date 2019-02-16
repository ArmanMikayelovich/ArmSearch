package project.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;

import org.springframework.web.servlet.ModelAndView;

import project.exception.ResourceNotFoundException;
import project.model.User;
import project.repository.UserRepository;
import project.service.PasswordEncrypter;
import project.service.UserService;

import javax.persistence.GeneratedValue;
import javax.validation.Valid;

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




    @PostMapping("/registration")
    User addNewUser(User user) {
        userService.saveUser(user);

       return  user;
    }




    // Get a Single User
    @GetMapping("/users/{id}")
    public User getUserById(@PathVariable(value = "id") Integer userId) {
        return userService.getUserById(userId);
    }//TODO ANI|||erb bacvi mardu ej@ piti haytararutyunner@ irar takic sharvac gan
    //  nkar@ dzax koxqic, ajic title,title -ic mi qich aj apranqi gin@,
    //  //title-i tak@ grvac lini Category, Category-i takic vejin tarmacman amsativ@


    // Update a User
    @PostMapping("/users/{id}")
    public User updateUser(@PathVariable(value = "id") Integer userId,
                           @Valid User user) {
        userService.updateUser(user);
        return user;
    }

    // Delete a User
    @DeleteMapping("/users/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable(value = "id") Integer userId) {
        User user = userService.findById(userId).
                    orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
        return ResponseEntity.ok().build();
    }

}
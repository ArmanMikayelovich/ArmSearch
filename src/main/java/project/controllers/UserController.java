package project.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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

import javax.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    UserRepository userRepository;




    // Get All Users
    @GetMapping("/users")
    public ModelAndView getAllUsers() {
        ModelAndView modelAndView = new ModelAndView("registerUser");
            modelAndView.addObject("users",userRepository.findAll());
        return modelAndView;
    }
    /////////
//    @PostMapping(path = "/users",  consumes = { MediaType.APPLICATION_JSON_VALUE },
//            headers = "application/x-www-form-urlencoded;charset=UTF-8",
//            produces = "application/json")
    @PostMapping("/users")
    User newUser(User user) {
        user.setPassword(PasswordEncrypter.encrypt(user.getPassword()));
        System.out.println(user);
        return userRepository.save(user);
    }




    // Get a Single User
    @GetMapping("/users/{id}")
    public User getUserById(@PathVariable(value = "id") Integer userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
    }//TODO ANI|||erb bacvi mardu ej@ piti haytararutyunner@ irar takic sharvac gan
    //  nkar@ dzax koxqic, ajic title,title -ic mi qich aj apranqi gin@,
    //  //title-i tak@ grvac lini Category, Category-i takic vejin tarmacman amsativ@


    // Update a User
    @PutMapping("/users/{id}")
    public User updateUser(@PathVariable(value = "id") Integer userId,
                           @Valid @RequestBody User userDetails) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

        user.setFirstName(userDetails.getFirstName());
        user.setLastName(userDetails.getLastName());
        user.setEmail(userDetails.getEmail());
        user.setPhoneNumber(userDetails.getPhoneNumber());
        user.setPassword(PasswordEncrypter.encrypt(userDetails.getPassword()));
        user.setItemList(userDetails.getItemList());//TODO ARO bacatri front ic productlist vonc es vercnelu...

        user.setItemList(userDetails.getItemList());

        User updatedUser = userRepository.save(user);
        return updatedUser;
    }

    // Delete a User
    @DeleteMapping("/users/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable(value = "id") Integer userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
        userRepository.delete(user);

        return ResponseEntity.ok().build();
    }

}
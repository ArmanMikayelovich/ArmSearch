package project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import project.dto.UserDto;
import project.exception.ResourceNotFoundException;
import project.model.User;
import project.repository.UserRepository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;




@Service
public class UserService  {

    private final UserRepository userRepository;
    @Autowired
    private  ItemService itemService;

    private PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();

    }

//    public UserService(UserRepository userRepository, ItemService itemService) {
//
//        this.userRepository = userRepository;
//        this.itemService = itemService;
//    }

    public UserService(UserRepository userRepository) {

        this.userRepository = userRepository;
    }


    public User getAuthenticatedUser() throws Exception{
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        org.springframework.security.core.userdetails.User springUser
                = (org.springframework.security.core.userdetails.User) auth.getPrincipal();


        return userRepository.findByEmail(springUser.getUsername());


    }
    @Transactional
    public void saveUser(User user) {

        user.setPassword(passwordEncoder().encode(user.getPassword()));
        System.out.println(user.getPassword());
        user.setItemList(new ArrayList<>());
        userRepository.save(user);
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(
                        user.getEmail(),
                       user.getPassword(),
                        new HashSet<GrantedAuthority>() {
                            {
                                new SimpleGrantedAuthority(user.getRoleName());
                            }
                        }
                ));

    }
    @Transactional

    public void updateUser(UserDto userDetails) {


        User user = null;
        try {
            user = getAuthenticatedUser();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (userDetails.getFirstName() != null) {
            user.setFirstName(userDetails.getFirstName());
        }
        if (userDetails.getLastName() != null) {
            user.setLastName(userDetails.getLastName());
        }

        if (userDetails.getEmail() != null) {
            user.setEmail(userDetails.getEmail());

        }

        if (userDetails.getPhoneNumber() != null) {
            user.setPhoneNumber(userDetails.getPhoneNumber());

        }
        userRepository.save(user);
    }

    @Transactional
    public void deleteUser(String password) {
        User user = null;
        try {
            user = getAuthenticatedUser();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (passwordEncoder().matches(password, user.getPassword())) {
            user.getItemList().forEach(itemService::deleteItem);
            userRepository.delete(user);
        }
    }

    public User getUserById(Integer id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public Optional<User> findById(Integer id) {
       return userRepository.findById(id);

    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email);

    }
    @Transactional
    public boolean changePassword(String oldPassword, String newPassword) {
        User user = null;
        try {
            user = getAuthenticatedUser();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (passwordEncoder().matches(oldPassword, user.getPassword())) {

            user.setPassword(passwordEncoder().encode(newPassword));
            userRepository.save(user);

            return true;
        } else return false;

    }

    public void deleteUserFromAdminPanel(int  id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Item", "id", id));
        user.getItemList()
                .forEach(itemService::deleteItem);
        userRepository.delete(user);
        userRepository.flush();
    }
    public Long getCountOfUsers() {
        return userRepository.count();

    }

    public List<User> getAllUsers() {
       return userRepository.findAll();
    }


}

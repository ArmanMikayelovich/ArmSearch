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
import project.exception.ResourceNotFoundException;
import project.model.User;
import project.repository.UserRepository;

import java.util.*;

@Service
public class UserService  {

    private final UserRepository userRepository;

    private PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();

    }
    public UserService(UserRepository userRepository) {

        this.userRepository = userRepository;
    }

    public User getAuthenticatedUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        org.springframework.security.core.userdetails.User springUser = (org.springframework.security.core.userdetails.User) auth.getPrincipal();
        User user = userRepository.findByEmail(springUser.getUsername());
        return user;
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
    public void updateUser(User userDetails) {

        User user = userRepository.findById(userDetails.getId())
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userDetails.getId()));

        user.setFirstName(userDetails.getFirstName());
        user.setLastName(userDetails.getLastName());
        user.setEmail(userDetails.getEmail());
        user.setPhoneNumber(userDetails.getPhoneNumber());
        user.setPassword(passwordEncoder().encode(userDetails.getPassword()));
        userRepository.save(user);
    }
    @Transactional
    public void deleteUser(User user) {
        userRepository.delete(user);
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

}

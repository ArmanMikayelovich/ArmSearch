package project.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import project.model.User;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
private final UserService userService;

    public UserDetailsServiceImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userService.findByEmail(s);
        return toUserDetails(user);
    }

     private UserDetails toUserDetails(User userObject) {
        return  org.springframework.security.core.userdetails.User
                    .withUsername(userObject.getEmail())
                .password(userObject.getPassword())
                .roles(userObject.getRoleName()).build();
    }

}

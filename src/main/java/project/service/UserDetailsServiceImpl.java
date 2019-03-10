/**
 * implementing UserDetailsService interface helps
 * this class to handle with userdetails
 */

package project.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import project.model.UserEntity;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
private final UserService userService;

    public UserDetailsServiceImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        UserEntity userEntity = userService.findByEmail(s);
        return toUserDetails(userEntity);
    }

     private UserDetails toUserDetails(UserEntity userEntityObject) {
        return  org.springframework.security.core.userdetails.User
                    .withUsername(userEntityObject.getEmail())
                .password(userEntityObject.getPassword())
                .roles(userEntityObject.getRoleName()).build();
    }

}

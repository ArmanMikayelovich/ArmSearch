package project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import project.dto.UserDto;
import project.exception.ResourceNotFoundException;
import project.model.Image;
import project.model.Item;
import project.model.User;
import project.repository.ImageRepository;
import project.repository.ItemRepository;
import project.repository.UserRepository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;




@Service
public class UserService  {

    private final UserRepository userRepository;
    private final ItemRepository itemRepository;
    private final ImageRepository imageRepository;
    private final DeletedImagesPathService deletedImagesPathService;
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

    public UserService(UserRepository userRepository, ItemRepository itemRepository, ImageRepository imageRepository, DeletedImagesPathService deletedImagesPathService) {

        this.userRepository = userRepository;
        this.itemRepository = itemRepository;
        this.imageRepository = imageRepository;
        this.deletedImagesPathService = deletedImagesPathService;
    }


    public User getAuthenticatedUser() throws Exception{

//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        org.springframework.security.core.userdetails.User springUser
//                = (org.springframework.security.core.userdetails.User) auth.getPrincipal();

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

            String username = ((UserDetails) principal).getUsername();
           User user = userRepository.findByEmail(username);


        return userRepository.findByEmail(username);


    }
    @Transactional
    public void saveUser(User user) {

        user.setPassword(passwordEncoder().encode(user.getPassword()));
        System.out.println(user.getPassword());
        user.setItemList(new ArrayList<>());
        userRepository.save(user);


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
            user.getItemList()
                    .forEach(item -> {
                        deletedImagesPathService.saveDeletedImagesPathFromImageList(item.getImageList());
                        item.getImageList().forEach(img -> {
                            imageRepository.delete(img);
                        });
                    });
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
    @Transactional
    public void deleteUserFromAdminPanel(int  id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
//        user.getItemList()
//                .forEach(item -> {
//                    deletedImagesPathService.saveDeletedImagesPathFromImageList(item.getImageList());
//                    item.getImageList().forEach(imageRepository::delete);
//                    itemRepository.delete(item);
//                });
        for (Item item : user.getItemList()) {
            deletedImagesPathService.saveDeletedImagesPathFromImageList(item.getImageList());
            for (Image img : item.getImageList()) {
                imageRepository.delete(img);
            }
            itemRepository.delete(item);
        }
        user.setItemList(null);

//        userRepository.deleteById(id);
        userRepository.delete(user);
    }
    public Long getCountOfUsers() {
        return userRepository.count();

    }

    public List<User> getAllUsers() {
       return userRepository.findAll();
    }


}

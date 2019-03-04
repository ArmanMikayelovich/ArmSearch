/**
 * this class calls the methods of UserRepository interface
 * in its own methods which are used in the controller layer.
 * This helps to divide the code into logical peaces
 */

package project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.dto.UserDto;
import project.exception.ResourceNotFoundException;
import project.model.ImageEntity;
import project.model.ItemEntity;
import project.model.UserEntity;
import project.repository.ImageRepository;
import project.repository.ItemRepository;
import project.repository.UserRepository;

import java.util.ArrayList;
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


    public UserService(UserRepository userRepository, ItemRepository itemRepository,
                       ImageRepository imageRepository,
                       DeletedImagesPathService deletedImagesPathService) {

        this.userRepository = userRepository;
        this.itemRepository = itemRepository;
        this.imageRepository = imageRepository;
        this.deletedImagesPathService = deletedImagesPathService;
    }


    public UserEntity getAuthenticatedUser() throws Exception{


        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

            String username = ((UserDetails) principal).getUsername();
           UserEntity userEntity = userRepository.findByEmail(username);


        return userRepository.findByEmail(username);


    }
    @Transactional
    public void saveUser(UserEntity userEntity) {

        userEntity.setPassword(passwordEncoder().encode(userEntity.getPassword()));
        System.out.println(userEntity.getPassword());
        userEntity.setItemEntityList(new ArrayList<>());
        userRepository.save(userEntity);


    }
    @Transactional

    public void updateUser(UserDto userDetails) {


        UserEntity userEntity = null;
        try {
            userEntity = getAuthenticatedUser();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (userDetails.getFirstName() != null) {
            userEntity.setFirstName(userDetails.getFirstName());
        }
        if (userDetails.getLastName() != null) {
            userEntity.setLastName(userDetails.getLastName());
        }

        if (userDetails.getEmail() != null) {
            userEntity.setEmail(userDetails.getEmail());

        }

        if (userDetails.getPhoneNumber() != null) {
            userEntity.setPhoneNumber(userDetails.getPhoneNumber());

        }
        userRepository.save(userEntity);
    }

    @Transactional
    public void deleteUser(String password) {
        UserEntity userEntity = null;
        try {
            userEntity = getAuthenticatedUser();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (passwordEncoder().matches(password, userEntity.getPassword())) {
            userEntity.getItemEntityList()
                    .forEach(item -> {
                        deletedImagesPathService.saveDeletedImagesPathFromImageList(item.getImageEntityList());
                        item.getImageEntityList().forEach(img -> {
                            imageRepository.delete(img);
                        });
                    });
            userRepository.delete(userEntity);
        }
    }

    public UserEntity getUserById(Integer id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("UserEntity", "id", id));
    }

    public List<UserEntity> findAll() {
        return userRepository.findAll();
    }

    public Optional<UserEntity> findById(Integer id) {
       return userRepository.findById(id);

    }

    public UserEntity findByEmail(String email) {
        return userRepository.findByEmail(email);

    }
    @Transactional
    public boolean changePassword(String oldPassword, String newPassword) {
        UserEntity userEntity = null;
        try {
            userEntity = getAuthenticatedUser();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (passwordEncoder().matches(oldPassword, userEntity.getPassword())) {

            userEntity.setPassword(passwordEncoder().encode(newPassword));
            userRepository.save(userEntity);

            return true;
        } else return false;

    }
    @Transactional
    public void deleteUserFromAdminPanel(int  id) {
        UserEntity userEntity = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("UserEntity", "id", id));

        for (ItemEntity itemEntity : userEntity.getItemEntityList()) {
            deletedImagesPathService.saveDeletedImagesPathFromImageList(itemEntity.getImageEntityList());
            for (ImageEntity img : itemEntity.getImageEntityList()) {
                imageRepository.delete(img);
            }
            itemRepository.delete(itemEntity);
        }

        userEntity.setItemEntityList(null);

        userRepository.delete(userEntity);
    }
    public Long getCountOfUsers() {
        return userRepository.count();

    }

    public List<UserEntity> getAllUsers() {
       return userRepository.findAll();
    }

}

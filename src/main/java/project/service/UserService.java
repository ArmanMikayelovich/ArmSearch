package project.service;

import org.springframework.stereotype.Service;
import project.exception.ResourceNotFoundException;
import project.model.User;
import project.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {

        this.userRepository = userRepository;
    }

    public void saveUser(User user) {

        user.setPassword(PasswordEncrypter.encrypt(user.getPassword()));
        userRepository.save(user);
    }

    public void updateUser(User userDetails) {

        User user = userRepository.findById(userDetails.getId())
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userDetails.getId()));

        user.setFirstName(userDetails.getFirstName());
        user.setLastName(userDetails.getLastName());
        user.setEmail(userDetails.getEmail());
        user.setPhoneNumber(userDetails.getPhoneNumber());
        user.setPassword(PasswordEncrypter.encrypt(userDetails.getPassword()));
        userRepository.save(user);
    }

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

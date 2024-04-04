package com.cobanoglu.denemebrain.service;

import com.cobanoglu.denemebrain.entity.User;
import com.cobanoglu.denemebrain.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public User createUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public User updateUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public boolean isValidUser(String username, String password) {
        User user = userRepository.findByEmail(username);
        if(user != null && user.getPassword().equals(password)) {
            return true;
        }
        return false;

    }

    @Override
    public boolean isUserExists(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public void saveUser(String firstName, String lastName, String educationLevel, String email, String password) {
        User user = new User();
        user.setUsername(firstName);
        user.setSurname(lastName);
        user.setEducationLevel(educationLevel);
        user.setEmail(email);
        user.setPassword(password);
        userRepository.save(user);
    }

    @Override
    public void saveUserAll(User user) {
        userRepository.save(user);
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public boolean findByEmail(String email) {
        return userRepository.findByEmail(email) != null;
    }


}




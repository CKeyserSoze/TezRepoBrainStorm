package com.cobanoglu.denemebrain.service;

import com.cobanoglu.denemebrain.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> getAllUsers();
    User getUserById(Long id);
    User createUser(User user);
    User updateUser(User user);
    void deleteUserById(Long id);
    boolean isValidUser(String username, String password);
    boolean isUserExists(String email);
    void saveUser(String firstName, String lastName, String educationLevel, String email, String password);
    void saveUserAll(User user);
    User getUserByEmail(String email);
    User findById(Long id);
    boolean findByEmail(String email);
}

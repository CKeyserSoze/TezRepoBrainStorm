package com.cobanoglu.denemebrain.service;

import com.cobanoglu.denemebrain.entity.User;
import com.cobanoglu.denemebrain.entity.VerificationToken;
import com.cobanoglu.denemebrain.repository.UserRepository;
import com.cobanoglu.denemebrain.repository.VerificationTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final VerificationTokenRepository verificationTokenRepository;
    private final EmailService emailService;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, VerificationTokenRepository verificationTokenRepository, EmailService emailService) {
        this.userRepository = userRepository;
        this.verificationTokenRepository = verificationTokenRepository;
        this.emailService = emailService;
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
        return userRepository.findByEmail(email) != null;
    }

    @Override
    public void saveUser(String firstName, String lastName, String educationLevel, String email, String password) {
        User user = new User();
        user.setUsername(firstName);
        user.setSurname(lastName);
        user.setEducationLevel(educationLevel);
        user.setEmail(email);
        user.setPassword(password);
        user.setUsed(false); // Kullanıcı başlangıçta doğrulanmamış
        userRepository.save(user);

        String token = UUID.randomUUID().toString();
        VerificationToken verificationToken = new VerificationToken(token, user, LocalDateTime.now().plusDays(1));
        verificationTokenRepository.save(verificationToken);

        String verificationLink = "http://localhost:8080/user/verify?token=" + token;
        emailService.sendEmail(user.getEmail(), "Email Doğrulama", "Email doğrulamak için lütfen bu linke tıklayın: " + verificationLink);
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

    @Override
    public void verifyUser(String token) {
        VerificationToken verificationToken = verificationTokenRepository.findByToken(token);
        if (verificationToken != null && !verificationToken.isUsed() && verificationToken.getExpiryDate().isAfter(LocalDateTime.now())) {
            User user = verificationToken.getUser();
            user.setUsed(true);
            userRepository.save(user);
            verificationToken.setUsed(true);
            verificationTokenRepository.save(verificationToken);
        }
    }



}




package com.cobanoglu.denemebrain.controller;

import com.cobanoglu.denemebrain.service.TeacherService;
import com.cobanoglu.denemebrain.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

@Controller
@RequestMapping("/user")
public class UserRegisterController {

    private final UserService userService;
    private final TeacherService teacherService;

    @Autowired
    public UserRegisterController(UserService userService, TeacherService teacherService) {
        this.userService = userService;
        this.teacherService = teacherService;
    }

    @GetMapping("register")
    public String showRegisterForm() {
        return "user_register";
    }

    @PostMapping("/register")
    public String registerUser(@RequestParam("first-name") String firstName,
                               @RequestParam("last-name") String lastName,
                               @RequestParam("education-level") String educationLevel,
                               @RequestParam("email") String email,
                               @RequestParam("new-password") String password,
                               @RequestParam("confirm-password") String confirmPassword,
                               Model model) {

        // Password validation
        if (!password.equals(confirmPassword)) {
            model.addAttribute("error", "Şifreler eşleşmiyor.");
            return "user_register";
        }

        // Check if email already exists
        if (userService.isUserExists(email) || teacherService.existsByEmail(email)) {
            model.addAttribute("error", "Bu emaile ait bir hesap zaten mevcut.");
            return "user_register";
        }

        userService.saveUser(firstName, lastName, educationLevel, email, password);
        model.addAttribute("success", "Kayıt başarılı! Lütfen emailinizi doğrulayın.");
        return "user_register";
    }

    @GetMapping("/verify")
    public String verifyUser(@RequestParam("token") String token, Model model) {
        userService.verifyUser(token);
        model.addAttribute("success", "Email doğrulandı! Giriş yapabilirsiniz.");
        return "user_login"; // Email doğrulandıktan sonra yönlendirilecek sayfa
    }
}

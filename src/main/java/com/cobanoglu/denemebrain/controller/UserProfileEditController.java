package com.cobanoglu.denemebrain.controller;

import com.cobanoglu.denemebrain.entity.User;
import com.cobanoglu.denemebrain.service.TeacherService;
import com.cobanoglu.denemebrain.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/user/home")
public class UserProfileEditController {

    private final UserService userService;

    private final TeacherService teacherService;


    public UserProfileEditController(UserService userService, TeacherService teacherService) {
        this.userService = userService;
        this.teacherService = teacherService;
    }

    @GetMapping("/{id}/profile/edit")
    public String showUserProfileEditPage(@PathVariable("id") Long userId, Model model){
        User user = userService.getUserById(userId);
        if(user!= null){
            model.addAttribute("id", userId);
            model.addAttribute("user", user);
                return "user_profile_edit";
        }
        else{
            return "error";
        }

    }

    @PostMapping("/{id}/profile/edit")
    public String updateUser(@PathVariable("id") Long userId,
                             @RequestParam("first_name") String firstName,
                             @RequestParam("last_name") String lastName,
                             @RequestParam("email") String email,
                             @RequestParam("password") String password,
                             @RequestParam("information") String information,
                             Model model) {

        if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || password.isEmpty()) {
            model.addAttribute("errorMessage", "Lütfen tüm alanları doldurun");
            return "user_profile_edit";
        }

        User user = userService.findById(userId);
        if (user == null) {
            model.addAttribute("errorMessage", "Kullanıcı bulunamadı.");
            return "error";
        }
        model.addAttribute("user", user);

        // Değiştirilen alanlara göre güncelleme yap
        if (!firstName.equals(user.getUsername())) {
            user.setUsername(firstName);
        }
        if (!lastName.equals(user.getSurname())) {
            user.setSurname(lastName);
        }
        if (!email.equals(user.getEmail())) {
            // Yeni e-posta adresinin sistemde başka bir kullanıcıya ait olup olmadığını kontrol et
            if (userService.getUserByEmail(email) != null || teacherService.getUserByEmail(email) != null) {
                model.addAttribute("errorMessage", "Bu email'e ait hesap zaten var.");
                return "user_profile_edit";
            }
            user.setEmail(email);
        }
        if (!password.equals(user.getPassword())) {
            user.setPassword(password);
        }
        if (!information.equals(user.getInformation())) {
            user.setInformation(information);
        }

        // Güncellenmiş kullanıcıyı kaydet
        userService.saveUserAll(user);

        // Başarılı güncelleme mesajı ekleyip, kullanıcıyı profil sayfasına yönlendir
        model.addAttribute("successMessage", "Profil başarıyla güncellendi.");
        return "redirect:/user/home/" + userId + "/profile";
    }


}


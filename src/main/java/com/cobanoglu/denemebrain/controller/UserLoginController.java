package com.cobanoglu.denemebrain.controller;

import com.cobanoglu.denemebrain.entity.Teacher;
import com.cobanoglu.denemebrain.entity.User;
import com.cobanoglu.denemebrain.service.TeacherService;
import com.cobanoglu.denemebrain.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;

@Controller
@RequestMapping("/user")
public class UserLoginController {

    private final UserService userService;
    private final TeacherService teacherService;

    public UserLoginController(UserService userService, TeacherService teacherService) {
        this.userService = userService;
        this.teacherService = teacherService;
    }

    @GetMapping("/login")
    public String showLoginForm() {
        // Giriş formunu gösterme işlemleri burada gerçekleştirilebilir
        return "user_login";
    }

    @PostMapping("/login")
    public String login(@RequestParam("email") String email, @RequestParam("password") String password, Model model) {
        // Kullanıcıyı e-posta adresine göre bul
        User user = userService.getUserByEmail(email);
        Teacher teacher = teacherService.findByEmail(email);
        boolean isValidUser = user != null && userService.isValidUser(user.getEmail(), password);
        boolean isValidTeacher = teacher != null && teacherService.isValidTeacher(teacher.getEmail(), password);

        if (isValidTeacher) {
            // Öğretmenin e-postası doğrulanmış mı kontrol et
            if (!teacher.isUsed()) {
                model.addAttribute("error", "Lütfen e-posta adresinizi doğrulayın.");
                return "user_login"; // E-posta doğrulanmadıysa hata mesajı göster
            }
            return "redirect:/teacher/user/" + teacher.getId();
        } else if (isValidUser) {
            // Kullanıcının e-postası doğrulanmış mı kontrol et
            if (!user.isUsed()) {
                model.addAttribute("error", "Lütfen e-posta adresinizi doğrulayın.");
                return "user_login"; // E-posta doğrulanmadıysa hata mesajı göster
            }
            // Kullanıcı doğrulandıysa kullanıcı kimliğini modelde tut
            model.addAttribute("user_id", user.getId());
            return "redirect:/user/home/" + user.getId();
        } else {
            model.addAttribute("error", "E-posta veya şifre yanlış");
            return "user_login"; // Yanlışsa login sayfasında hata göster
        }
    }
}
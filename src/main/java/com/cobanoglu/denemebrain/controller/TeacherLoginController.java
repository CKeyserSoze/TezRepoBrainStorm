package com.cobanoglu.denemebrain.controller;

import org.springframework.ui.Model;
import com.cobanoglu.denemebrain.entity.Teacher;
import com.cobanoglu.denemebrain.service.TeacherService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/teacher")
public class TeacherLoginController {

    private final TeacherService teacherService;

    public TeacherLoginController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @GetMapping("/login")
    public String showLoginForm() {
        return "teacher_login";
    }

    @PostMapping("/login")
    public String login(@RequestParam("email") String email,
                        @RequestParam("password") String password,
                        Model model) {
        boolean isValidTeacher = teacherService.isValidTeacher(email, password);
        if (isValidTeacher) {
            Teacher teacher = teacherService.findByEmail(email);
            return "redirect:/teacher/user/" + teacher.getId(); // Doğruysa index.html'e yönlendir
        } else {
            model.addAttribute("error", "Kullanıcı adı veya şifre yanlış");
            return "teacher_login"; // Yanlışsa login sayfasında hata göster
        }
    }
}

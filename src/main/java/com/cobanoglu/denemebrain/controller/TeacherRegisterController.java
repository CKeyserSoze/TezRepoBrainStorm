package com.cobanoglu.denemebrain.controller;

import com.cobanoglu.denemebrain.entity.Teacher;
import com.cobanoglu.denemebrain.repository.TeacherRepository;
import com.cobanoglu.denemebrain.service.TeacherService;
import com.cobanoglu.denemebrain.service.ExpertiseService;
import com.cobanoglu.denemebrain.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/teacher")
public class TeacherRegisterController {

    private final TeacherService teacherService;
    private final ExpertiseService expertiseService;
    private final UserService userService;

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    public TeacherRegisterController(TeacherService teacherService, ExpertiseService expertiseService,UserService userService) {
        this.teacherService = teacherService;
        this.expertiseService = expertiseService;
        this.userService = userService;
    }

    @GetMapping("/register")
    public String showRegisterForm(){
        return "teacher_register";
    }

    @PostMapping("/register")
    public String registerTeacher(@RequestParam("first-name") String firstName,
                                  @RequestParam("last-name") String lastName,
                                  @RequestParam("email") String email,
                                  @RequestParam("new-password") String password,
                                  @RequestParam("confirm-password") String confirmPassword,
                                  @RequestParam("education") String education,
                                  @RequestParam("expertise") String expertiseArea,
                                  @RequestParam("experience") String experience,
                                  Model model) {

        // Passwordlerin eşleşip eşleşmediğini kontrol et
        if (!password.equals(confirmPassword)) {
            model.addAttribute("error", "Şifreler eşleşmiyor.");
            return "redirect:/teacher/register";
        }

        // Email'in veritabanında olup olmadığını kontrol et
        if (teacherService.existsByEmail(email) || userService.isUserExists(email)) {
            var blah = expertiseService.getAllExpertises();
            model.addAttribute("error", "Bu e-posta zaten kayıtlı.");
            return "teacher_register";
        }

        Teacher teacher = new Teacher(firstName, lastName, email, password, education);
        teacherRepository.save(teacher);

        // Uzmanlık alanlarını kaydet
        expertiseService.saveAllExpertise(expertiseArea, experience, teacher);

        return "redirect:/teacher/login"; // Öğretmen giriş sayfasına yönlendir
    }
}

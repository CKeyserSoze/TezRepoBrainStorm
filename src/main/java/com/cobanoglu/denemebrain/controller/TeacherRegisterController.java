package com.cobanoglu.denemebrain.controller;

import com.cobanoglu.denemebrain.entity.Teacher;
import com.cobanoglu.denemebrain.repository.TeacherRepository;
import com.cobanoglu.denemebrain.service.EmailService;
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

import jakarta.mail.MessagingException;
import java.util.UUID;

@Controller
@RequestMapping("/teacher")
public class TeacherRegisterController {

    private final TeacherService teacherService;
    private final ExpertiseService expertiseService;
    private final UserService userService;
    private final EmailService emailService;

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    public TeacherRegisterController(TeacherService teacherService, ExpertiseService expertiseService, UserService userService, EmailService emailService) {
        this.teacherService = teacherService;
        this.expertiseService = expertiseService;
        this.userService = userService;
        this.emailService = emailService;
    }

    @GetMapping("/register")
    public String showRegisterForm() {
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
            return "teacher_register";
        }

        // Email'in veritabanında olup olmadığını kontrol et
        if (teacherService.existsByEmail(email) || userService.isUserExists(email)) {
            model.addAttribute("error", "Bu e-posta zaten kayıtlı.");
            return "teacher_register";
        }

        Teacher teacher = new Teacher(firstName, lastName, email, password, education);
        // Öğretmen için doğrulama token'ı oluşturma
        String verificationToken = UUID.randomUUID().toString();
        teacher.setVerificationToken(verificationToken);
        teacherRepository.save(teacher);

        // Uzmanlık alanlarını kaydet
        expertiseService.saveAllExpertise(expertiseArea, experience, teacher);

        // Doğrulama emaili gönderme işlemi
        String verificationLink = "http://localhost:8080/teacher/verify?token=" + verificationToken;
        String emailText = "Lütfen e-posta adresinizi doğrulamak için aşağıdaki linke tıklayın: \n" + verificationLink;
        try {
            emailService.sendVerificationEmail(email, "E-posta Doğrulama", emailText);
        } catch (MessagingException e) {
            model.addAttribute("error", "Doğrulama e-postası gönderilirken bir hata oluştu.");
            return "teacher_register";
        }

        model.addAttribute("success", "Kayıt başarılı! Lütfen emailinizi doğrulayın.");
        return "teacher_register";
    }

    @GetMapping("/verify")
    public String verifyTeacher(@RequestParam("token") String token, Model model) {
        teacherService.verifyTeacher(token);
        model.addAttribute("success", "Email doğrulandı! Giriş yapabilirsiniz.");
        return "user_login"; // Email doğrulandıktan sonra yönlendirilecek sayfa
    }
}

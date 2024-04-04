package com.cobanoglu.denemebrain.controller;

import com.cobanoglu.denemebrain.entity.Expertise;
import com.cobanoglu.denemebrain.entity.Teacher;
import com.cobanoglu.denemebrain.service.ExpertiseService;
import com.cobanoglu.denemebrain.service.TeacherService;
import com.cobanoglu.denemebrain.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/teacher/user")
public class TeacherProfileEditController {

    private TeacherService teacherService;
    private ExpertiseService expertiseService;
    private UserService userService;

    public TeacherProfileEditController(TeacherService teacherService, ExpertiseService expertiseService, UserService userService) {
        this.teacherService = teacherService;
        this.expertiseService = expertiseService;
        this.userService = userService;
    }

    @GetMapping("/{id}/profile/edit")
    public String showTeacherProfileEditPage(@PathVariable Long id, Model model) {
        Teacher teacher = teacherService.findById(id);
        List<Expertise> expertises = expertiseService.findByTeacherId(id);

        // Eğer teacher ve expertises null değilse devam edilir
        if (teacher != null && expertises != null) {
            model.addAttribute("id", id);
            model.addAttribute("teacher", teacher);

            // İlk uzmanlık alanı ve deneyim yılı alınır
            if (!expertises.isEmpty()) {
                Expertise firstExpertise = expertises.get(0);
                model.addAttribute("expertiseArea", firstExpertise.getArea());
                model.addAttribute("yearsOfExperience", firstExpertise.getYearsOfExperience());
            }

            return "teacher_profile_edit";
        } else {
            return "error";
        }
    }

    @PostMapping("/{id}/profile/edit")
    public String updateTeacher(@PathVariable Long id,
                                @RequestParam("first_name") String firstName,
                                @RequestParam("last_name") String lastName,
                                @RequestParam("email") String email,
                                @RequestParam("password") String password,
                                @RequestParam("educationLevel") String educationLevel,
                                @RequestParam("expertise") String expertiseArea,
                                @RequestParam("yearsOfExperience") String yearsOfExperience,
                                @RequestParam("information") String information,
                                Model model) {

        Teacher teacher = teacherService.findById(id);
        List<Expertise> expertises = expertiseService.findByTeacherId(id);


        if (teacher == null) {
            model.addAttribute("errorMessage", "Kullanıcı bulunamadı.");
            return "error";
        }

        model.addAttribute("teacher", teacher);

        if (!firstName.equals(teacher.getName())) {
            teacher.setName(firstName);
        }
        if (!lastName.equals(teacher.getSurname())) {
            teacher.setSurname(lastName);
        }
        if (!email.equals(teacher.getEmail())) {
            // Yeni e-posta adresinin sistemde başka bir kullanıcıya ait olup olmadığını kontrol edelim
            if (userService.getUserByEmail(email) != null || teacherService.getUserByEmail(email) != null) {
                model.addAttribute("errorMessage", "Bu email'e ait hesap zaten var.");
                return "teacher_profile_edit";
            }
            teacher.setEmail(email);
        }
        if (!password.equals(teacher.getPassword())) {
            teacher.setPassword(password);
        }
        if (!educationLevel.equals(teacher.getEducationLevel())) {
            teacher.setEducationLevel(educationLevel);
        }

        if (!information.equals(teacher.getTeacherInformation())) {
            teacher.setTeacherInformation(information);
        }

        Expertise selectedExpertise = null;
        for (Expertise expertise : expertises) {
            if (expertise.getArea().equals(expertiseArea)) {
                // Aynı uzmanlık alanına sahip bir uzmanlık bulundu
                if (expertise.getYearsOfExperience().equals(yearsOfExperience)) {
                    // Aynı deneyim yılına sahip bir uzmanlık bulundu, mevcut uzmanlık kullanılacak
                    selectedExpertise = expertise;
                    break;
                } else {
                    // Yıllar farklı, mevcut uzmanlığın yılını güncelle
                    expertise.setYearsOfExperience(yearsOfExperience);
                    expertiseService.saveExpertise(expertise);
                    return "redirect:/teacher/user/" + id + "/profile";
                }
            }
        }

// Eğer seçili uzmanlık alanı yoksa, ekleyin
        if (selectedExpertise == null) {
            selectedExpertise = new Expertise();
            selectedExpertise.setArea(expertiseArea);
            selectedExpertise.setYearsOfExperience(yearsOfExperience);
            selectedExpertise.setTeacher(teacher);
            expertises.add(selectedExpertise);
            expertiseService.saveAllExpertise(expertiseArea, yearsOfExperience, teacher);
        }
        teacherService.saveTeacherAll(teacher);

        return "redirect:/teacher/user/" + id + "/profile";

    }

}

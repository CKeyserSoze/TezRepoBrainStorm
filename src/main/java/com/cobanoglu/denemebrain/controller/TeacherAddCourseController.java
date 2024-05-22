package com.cobanoglu.denemebrain.controller;

import com.cobanoglu.denemebrain.entity.Course;
import com.cobanoglu.denemebrain.entity.Teacher;
import com.cobanoglu.denemebrain.service.CourseService;
import com.cobanoglu.denemebrain.service.TeacherService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.StringJoiner;

@Controller
@RequestMapping("/teacher/user")
public class TeacherAddCourseController {

    private final CourseService courseService;
    private final TeacherService teacherService;

    public TeacherAddCourseController(CourseService courseService, TeacherService teacherService) {
        this.courseService = courseService;
        this.teacherService = teacherService;
    }

    @GetMapping("/{id}/addcourse")
    public String showAddCourseForm(@PathVariable("id") Long id, Model model) {
        model.addAttribute("id", id);
        model.addAttribute("teacher", new Teacher());
        return "teacher_addcourse";
    }

    @PostMapping("/{id}/addcourse")
    public String addCourse(@PathVariable("id") Long teacherId,
                            @RequestParam("courseName") String courseName,
                            @RequestParam("description") String description,
                            @RequestParam("price") String price,
                            @RequestParam("classLevel") String classLevel,
                            @RequestParam("courseSubject") String courseSubject,
                            @RequestParam("availableTimes") String availableTimes,
                            @RequestParam("availableTimesAsHours") String availableTimesAsHours,
                            Model model) {

        // Kurs bilgilerinin doğruluğunu kontrol et
        if (courseName.isEmpty() || description.isEmpty() || price == null) {
            model.addAttribute("errorMessage", "Lütfen tüm alanları doldurun.");
            return "teacher_addcourse";
        }

        // Öğretmenin varlığını doğrula
        Teacher teacher = teacherService.findById(teacherId);
        if (teacher == null) {
            model.addAttribute("errorMessage", "Öğretmen bulunamadı.");
            return "teacher_addcourse";
        }

        // Müsait zamanları işle ve formatla
        String formattedAvailableTimes = formatAvailableTimes(availableTimes);
        String formattedAvailableHours = formatAvailableHours(availableTimesAsHours);

        // Kursu kaydet
        int parsedPrice = Integer.parseInt(price);
        Course course = new Course(courseName, description, parsedPrice, "/static/images/course-image2.jpg", classLevel, courseSubject, teacher, formattedAvailableTimes, formattedAvailableHours);
        courseService.save(course);

        // Başarılı mesajı ekle ve öğretmenin profil sayfasına yönlendir
        model.addAttribute("successMessage", "Kurs başarıyla eklendi.");
        return "redirect:/teacher/user/" + teacherId;
    }

    private String formatAvailableTimes(String availableTimes) {
        StringJoiner formattedAvailableTimes = new StringJoiner(";");
        // Örneğin, verileri belirli bir formatta işleyebilirsiniz
        String[] times = availableTimes.split(", ");
        for (String time : times) {
            formattedAvailableTimes.add(time);
        }
        return formattedAvailableTimes.toString();
    }

    private String formatAvailableHours(String availableTimesAsHours) {
        StringJoiner formattedAvailableHours = new StringJoiner(";");
        // Örneğin, verileri belirli bir formatta işleyebilirsiniz
        String[] hours = availableTimesAsHours.split(", ");
        for (String hour : hours) {
            formattedAvailableHours.add(hour);
        }
        return formattedAvailableHours.toString();
    }
}

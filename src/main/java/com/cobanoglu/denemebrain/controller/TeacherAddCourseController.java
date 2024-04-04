package com.cobanoglu.denemebrain.controller;

import com.cobanoglu.denemebrain.entity.Course;
import com.cobanoglu.denemebrain.entity.Teacher;
import com.cobanoglu.denemebrain.service.CourseService;
import com.cobanoglu.denemebrain.service.TeacherService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
        model.addAttribute("id",id);
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
        int parsedPrice = Integer.parseInt(price);
        // Kursu kaydet
        Course course = new Course(courseName, description, parsedPrice, "/static/images/course-image2.jpg", classLevel, courseSubject, teacher);
        courseService.save(course);

        // Başarılı mesajı ekle ve öğretmenin profil sayfasına yönlendir
        model.addAttribute("successMessage", "Kurs başarıyla eklendi.");
        return "redirect:/teacher/user/" + teacherId;
    }
}
package com.cobanoglu.denemebrain.controller;

import com.cobanoglu.denemebrain.entity.Course;
import com.cobanoglu.denemebrain.service.CourseService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/teacher/user")
public class CourseEditController {

    private CourseService courseService;

    public CourseEditController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping("/{id}/course/edit/{courseId}")
    public String showCourseEditPage(@PathVariable("id") Long id,
                                     @PathVariable("courseId") Long courseId,
                                     Model model){


        Optional<Course> optionalCourse = courseService.getCourseById(courseId);
        optionalCourse.ifPresentOrElse(
                course -> model.addAttribute("course", course),
                () -> model.addAttribute("course", new Course())
        );

        return "teacher_course_edit";

    }

    @PostMapping("/{id}/course/edit/{courseId}")
    public String updateCourse(@PathVariable("id") Long id,
                               @PathVariable("courseId") Long courseId,
                               @RequestParam("course_name") String courseName,
                               @RequestParam("course_description") String courseDescription,
                               @RequestParam("course_price") int coursePrice,
                               Model model){

        if (courseName.isBlank() || courseDescription.isBlank() ) {
            model.addAttribute("errorMessage", "Kurs adı, açıklaması ve fiyatı boş bırakılamaz.");
            return "teacher_course_edit"; // Aynı sayfaya geri döndür
        }


        Optional<Course> optionalCourse = courseService.getCourseById(courseId);
        if (optionalCourse.isPresent()) {
            Course course = optionalCourse.get();

            // Yeni değerlerle mevcut kursu güncelle
            course.setName(courseName);
            course.setDescription(courseDescription);
            course.setPrice(coursePrice);

            // Güncellenmiş kursu kaydetmek için service katmanını kullanın
            courseService.save(course);

            // Başarılı bir şekilde güncellendiğini belirtmek için bir mesaj ekleyebilirsiniz
            model.addAttribute("successMessage", "Kurs başarıyla güncellendi.");
        } else {
            // Kurs bulunamazsa bir hata mesajı ekleyebilirsiniz
            model.addAttribute("errorMessage", "Kurs bulunamadı.");
        }

        // Güncellenen kursun detaylarını göstermek için ilgili sayfaya yönlendirin
        return "redirect:/teacher/user/{id}/mycourses";
    }



 }

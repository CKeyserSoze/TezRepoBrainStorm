package com.cobanoglu.denemebrain.controller;

import com.cobanoglu.denemebrain.entity.Course;
import com.cobanoglu.denemebrain.entity.Teacher;
import com.cobanoglu.denemebrain.service.CourseService;
import com.cobanoglu.denemebrain.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/teacher")
public class TeacherUserController {

    private TeacherService teacherService;
    private CourseService courseService;

    @Autowired
    public TeacherUserController(TeacherService teacherService, CourseService courseService) {
        this.teacherService = teacherService;
        this.courseService = courseService;
    }

    @GetMapping("/user/{id}")
    public String showTeacherUserPage(@PathVariable Long id, Model model) {
        Teacher teacher = teacherService.findById(id);
        if (teacher != null) {
            List<Course> courses = courseService.getAllCourses();
            model.addAttribute("courses",courses);
            model.addAttribute("teacher", teacher);
            return "teacher_user";
        } else {
            // Öğretmen bulunamazsa hata sayfasına yönlendirme yapabilirsiniz
            return "redirect:/error";
        }
    }
}
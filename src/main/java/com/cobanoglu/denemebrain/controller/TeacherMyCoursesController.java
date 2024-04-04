package com.cobanoglu.denemebrain.controller;

import com.cobanoglu.denemebrain.entity.Course;
import com.cobanoglu.denemebrain.entity.Teacher;
import com.cobanoglu.denemebrain.service.CourseService;
import com.cobanoglu.denemebrain.service.TeacherService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/teacher/user")
public class TeacherMyCoursesController {

    private TeacherService teacherService;
    private CourseService courseService;

    public TeacherMyCoursesController(TeacherService teacherService, CourseService courseService) {
        this.teacherService = teacherService;
        this.courseService = courseService;
    }

    @GetMapping("/{id}/mycourses")
    public String showMyCoursesPage(@PathVariable Long id, Model model){
        Teacher teacher =teacherService.findById(id);
        List<Course> courses = courseService.findByTeacherId(id);
        if (teacher != null) {
            model.addAttribute("teacher", teacher);
            model.addAttribute("courses", courses);
            return "teacher_mycourses";
        } else {
            // Öğretmen bulunamazsa hata sayfasına yönlendirme yapabilirsiniz
            return "redirect:/error";
        }
    }
}

package com.cobanoglu.denemebrain.controller;

import com.cobanoglu.denemebrain.entity.Course;
import com.cobanoglu.denemebrain.entity.Expertise;
import com.cobanoglu.denemebrain.entity.Teacher;
import com.cobanoglu.denemebrain.service.CourseService;
import com.cobanoglu.denemebrain.service.ExpertiseService;
import com.cobanoglu.denemebrain.service.TeacherService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/teacher/user")
public class TeacherProfileController {

    private TeacherService teacherService;
    private CourseService courseService;
    private ExpertiseService expertiseService;

    public TeacherProfileController(TeacherService teacherService, CourseService courseService, ExpertiseService expertiseService) {
        this.teacherService = teacherService;
        this.courseService = courseService;
        this.expertiseService = expertiseService;
    }

    @GetMapping("/{id}/profile")
    public String showTeacherProfilePage(@PathVariable Long id, Model model){
        Teacher teacher = teacherService.findById(id);
        List<Course> courses = courseService.findByTeacherId(id);
        List<Expertise> expertises = expertiseService.findByTeacherId(id);


        if(teacher == null){
            return "error";
        }

        model.addAttribute("firstName", teacher.getName());
        model.addAttribute("lastName", teacher.getSurname());
        model.addAttribute("email", teacher.getEmail());
        model.addAttribute("password", teacher.getPassword());
        model.addAttribute("educationLevel", teacher.getEducationLevel());
        model.addAttribute("personalInfo", teacher.getTeacherInformation());
        model.addAttribute("expertise", expertises);
        model.addAttribute("teacher_courses", courses);

        return "teacher_profile";
    }

}

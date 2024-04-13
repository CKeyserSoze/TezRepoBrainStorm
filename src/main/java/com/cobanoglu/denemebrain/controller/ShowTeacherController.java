package com.cobanoglu.denemebrain.controller;

import com.cobanoglu.denemebrain.entity.Course;
import com.cobanoglu.denemebrain.entity.Expertise;
import com.cobanoglu.denemebrain.entity.Teacher;
import com.cobanoglu.denemebrain.service.CourseService;
import com.cobanoglu.denemebrain.service.ExpertiseService;
import com.cobanoglu.denemebrain.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/user/home")
public class ShowTeacherController {

    private TeacherService teacherService;

    private ExpertiseService expertiseService;

    private CourseService courseService;

    @Autowired
    public ShowTeacherController(TeacherService teacherService, ExpertiseService expertiseService, CourseService courseService) {
        this.teacherService = teacherService;
        this.expertiseService = expertiseService;
        this.courseService = courseService;
    }

    @GetMapping("{id}/teacher/{teacherId}")
    public String showTeacherPage(@PathVariable("teacherId") Long teacherId,
                                  @PathVariable("id") Long id,
                                  Model model){

        Teacher teacher = teacherService.findById(teacherId);
        List<Expertise> expertises = expertiseService.findByTeacherId(teacherId);
        List<Course> courses = courseService.findByTeacherId(teacherId);

        model.addAttribute("teacher", teacher);
        model.addAttribute("expertise", expertises);
        model.addAttribute("teacher_courses", courses);

        return "show_teacher";
    }

}

package com.cobanoglu.denemebrain.controller;

import com.cobanoglu.denemebrain.entity.Course;
import com.cobanoglu.denemebrain.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class IndexController {

    private CourseService courseService;

    @Autowired
    public IndexController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping("/home")
    public String index(Model model){

        List<Course> courses = courseService.getAllCourses();
        model.addAttribute("courses",courses);

        return "index";
    }
}

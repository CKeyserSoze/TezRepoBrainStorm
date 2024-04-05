package com.cobanoglu.denemebrain.controller;

import com.cobanoglu.denemebrain.entity.Course;
import com.cobanoglu.denemebrain.entity.User;
import com.cobanoglu.denemebrain.service.CourseService;
import com.cobanoglu.denemebrain.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserHomePageController {

    private UserService userService;
    private CourseService courseService;

    @Autowired
    public UserHomePageController(UserService userService, CourseService courseService) {
        this.userService = userService;
        this.courseService = courseService;
    }

    @GetMapping("/home/{id}")
    public String showHomePageForm(@PathVariable Long id, Model model){
        User user = userService.findById(id);
        if(user != null){
            List<Course> courses = courseService.getAllCourses();
            model.addAttribute("courses",courses);
            model.addAttribute("user", user);
            return "user_homepage";
        }
        else{
            return "redirect:/error";
        }



    }
    @GetMapping("/home/{id}/{grade}/{Lesson}")
    public String showHomePageForm(@PathVariable Long id,@PathVariable String grade,@PathVariable String Lesson, Model model){
        User user = userService.findById(id);
        if(user != null){
            int grade2 = Integer.parseInt(grade);
            List<Course> courses = courseService.getCourseByGradeAndLesson(grade,Lesson);


            model.addAttribute("courses",courses);
            model.addAttribute("user", user);
            return "user_homepage";
        }
        else{
            return "redirect:/error";
        }



    }
}

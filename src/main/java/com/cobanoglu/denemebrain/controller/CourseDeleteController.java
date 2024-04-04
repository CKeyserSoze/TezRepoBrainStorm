package com.cobanoglu.denemebrain.controller;

import com.cobanoglu.denemebrain.service.CourseService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/teacher/user")
public class CourseDeleteController {
    private CourseService courseService;

    public CourseDeleteController(CourseService courseService) {
        this.courseService = courseService;
    }


    @GetMapping("/{id}/course/delete/{courseId}")
    public String showDeletePage(@PathVariable("id") Long id,
                                 @PathVariable("courseId") Long courseId,
                                 Model model){

        model.addAttribute("id", id);
        model.addAttribute("courseId", courseId);
        return "delete_course_confirmation";

    }


    @PostMapping("/{id}/course/delete/{courseId}")
    public String deleteCourse(@PathVariable("id") Long id,
                               @PathVariable("courseId") Long courseId,
                               Model model){

        model.addAttribute("id",id);
        courseService.deleteCourseById(courseId);
        return "redirect:/teacher/user/{id}/mycourses";
    }
}

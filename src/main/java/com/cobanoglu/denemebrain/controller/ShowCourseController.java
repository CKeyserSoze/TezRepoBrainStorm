package com.cobanoglu.denemebrain.controller;

import com.cobanoglu.denemebrain.entity.Course;
import com.cobanoglu.denemebrain.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user/home")
public class ShowCourseController {

    private final CourseService courseService;

    @Autowired
    public ShowCourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping("/{id}/course/{courseId}")
    public String showCoursePage(@PathVariable("id") Long userId,
                                 @PathVariable("courseId") Long courseId,
                                 Model model){

        Course course = courseService.findById(courseId);
        model.addAttribute("courses", course);

        return "show_course";
    }
}

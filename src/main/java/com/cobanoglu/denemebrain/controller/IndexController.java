package com.cobanoglu.denemebrain.controller;

import com.cobanoglu.denemebrain.entity.Course;
import com.cobanoglu.denemebrain.entity.User;
import com.cobanoglu.denemebrain.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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
    @GetMapping("/home/{lesson}")
    public String CategorizedIndexPageByLesson( @PathVariable String lesson,
                                                Model model ){
        List<Course> courses = courseService.getCourseByLesson(lesson);
        model.addAttribute("courses",courses);

        return "categorized_index";
    }
    @GetMapping("/home/{grade}/{lesson}")
    public String CategorizedIndexPageByGradeAndLesson( @PathVariable String grade,
                                                        @PathVariable String lesson,
                                                        Model model ){
        List<Course> courses = courseService.getCourseByGradeAndLesson(grade,lesson);
        model.addAttribute("courses",courses);

        return "categorized_index";
    }
    @PostMapping("/home/search")
    public String search( @RequestParam("searchedWord") String searchedWord, Model model){

        List<Course> allCourses = courseService.getAllCourses();
        ArrayList<Course> courses = new ArrayList<>();
        for(var course : allCourses)
        {
            if(course.getDescription().toUpperCase().contains(searchedWord.toUpperCase())
                    || course.getName().toUpperCase().contains(searchedWord.toUpperCase())
                    || course.getLesson().toUpperCase().contains(searchedWord.toUpperCase()))
            {
                courses.add(course);
            }
        }
        model.addAttribute("courses",courses);
        return "categorized_index";
    }
}

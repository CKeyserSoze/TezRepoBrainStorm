package com.cobanoglu.denemebrain.controller;

import com.cobanoglu.denemebrain.entity.Course;
import com.cobanoglu.denemebrain.entity.User;
import com.cobanoglu.denemebrain.service.CourseService;
import com.cobanoglu.denemebrain.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
    @GetMapping("/home/{id}/filter/{filter}")
    public String filter(@PathVariable Long id, @PathVariable String filter, Model model){

        User user = userService.findById(id);
        List<Course> filteredCourses = courseService.getCoursesFilteredBy(filter);
        // Model üzerinden görünüme ilet
        model.addAttribute("user", user);
        model.addAttribute("courses", filteredCourses);
        model.addAttribute("filter", filter);

        return "categorized_user_homepage";

    }
    @GetMapping("/home/{id}/{Lesson}")
    public String showHomePageWithLesson(@PathVariable Long id,
                                         @PathVariable String Lesson,
                                         Model model){
        User user = userService.getUserById(id);
        List<Course> courses = courseService.getCourseByLesson(Lesson);
        
        model.addAttribute("courses",courses);
        model.addAttribute("user",user);
        return "categorized_user_homepage";
    }
    @GetMapping("/home/{id}/{grade}/{Lesson}")
    public String showHomePageWithGradeAndLesson(@PathVariable Long id,
                                   @PathVariable String grade,
                                   @PathVariable String Lesson,
                                   Model model){
        User user = userService.findById(id);
        if(user != null){
            List<Course> courses = courseService.getCourseByGradeAndLesson(grade,Lesson);


            model.addAttribute("courses",courses);
            model.addAttribute("user", user);
            return "categorized_user_homepage";
        }
        else{
            return "redirect:/error";
        }
    }
    @PostMapping("/home/{id}/search")
    public String search(@PathVariable Long id, @RequestParam("searchedWord") String searchedWord, Model model){

        User user = userService.findById(id);
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
        model.addAttribute("user", user);
        return "categorized_user_homepage";
    }

}

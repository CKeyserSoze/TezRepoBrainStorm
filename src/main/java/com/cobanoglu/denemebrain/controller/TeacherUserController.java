package com.cobanoglu.denemebrain.controller;

import com.cobanoglu.denemebrain.entity.Course;
import com.cobanoglu.denemebrain.entity.Teacher;
import com.cobanoglu.denemebrain.entity.User;
import com.cobanoglu.denemebrain.service.CourseService;
import com.cobanoglu.denemebrain.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

import java.util.ArrayList;
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
    @GetMapping("/user/{id}/filter/{filter}")
    public String filter(@PathVariable Long id, @PathVariable String filter, Model model){

        Teacher teacher = teacherService.findById(id);
        List<Course> filteredCourses = courseService.getCoursesFilteredBy(filter);
        // Model üzerinden görünüme ilet
        model.addAttribute("user", teacher);
        model.addAttribute("courses", filteredCourses);
        model.addAttribute("filter", filter);

        return "categorized_teacher_homepage";

    }
    @GetMapping("/user/{id}/{lesson}")
    public String showCategorizedPageWithLesson(@PathVariable Long id,
                                                @PathVariable String lesson,
                                                Model model){
        Teacher teacher = teacherService.findById(id);
        List<Course> courses = courseService.getCourseByLesson(lesson);

        model.addAttribute("courses",courses);
        model.addAttribute("teacher", teacher);
        return "categorized_teacher_homepage";
    }
    @GetMapping("/user/{id}/{grade}/{lesson}")
    public String showCategorizedPage(@PathVariable Long id,
                                      @PathVariable String grade,
                                      @PathVariable String lesson,
                                      Model model){
        Teacher teacher = teacherService.findById(id);
        if(teacher != null){
            int grade2 = Integer.parseInt(grade);
            List<Course> courses = courseService.getCourseByGradeAndLesson(grade,lesson);


            model.addAttribute("courses",courses);
            model.addAttribute("teacher", teacher);
            return "categorized_teacher_homepage";
        }
        else{
            return "redirect:/error";
        }
    }
    @PostMapping("/user/{id}/search")
    public String SearchByWord(@PathVariable Long id,
                               @RequestParam("searchedWord") String searchedWord,
                               Model model){
        Teacher teacher = teacherService.findById(id);
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
        model.addAttribute("teacher", teacher);
        return "categorized_teacher_homepage";

    }
}
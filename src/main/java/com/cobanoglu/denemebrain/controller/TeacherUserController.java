package com.cobanoglu.denemebrain.controller;

import com.cobanoglu.denemebrain.entity.Course;
import com.cobanoglu.denemebrain.entity.Teacher;
import com.cobanoglu.denemebrain.service.CourseService;
import com.cobanoglu.denemebrain.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/teacher")
public class TeacherUserController {

    private final TeacherService teacherService;
    private final CourseService courseService;

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
            model.addAttribute("courses", courses);
            model.addAttribute("teacher", teacher);
            return "teacher_user";
        } else {
            return "redirect:/error";
        }
    }

    @GetMapping("/user/{id}/filter/{filter}")
    public String filter(@PathVariable Long id, @PathVariable String filter, Model model) {
        Teacher teacher = teacherService.findById(id);
        List<Course> filteredCourses = courseService.getCoursesFilteredBy(filter);
        model.addAttribute("teacher", teacher);
        model.addAttribute("courses", filteredCourses);
        model.addAttribute("filter", filter);
        return "categorized_teacher_homepage";
    }

    @GetMapping("/user/{id}/{lesson}")
    public String showCategorizedPageWithLesson(@PathVariable Long id, @PathVariable String lesson, Model model) {
        Teacher teacher = teacherService.findById(id);
        List<Course> courses = courseService.getCourseByLesson(lesson);
        model.addAttribute("courses", courses);
        model.addAttribute("teacher", teacher);
        return "categorized_teacher_homepage";
    }

    @GetMapping("/user/{id}/{grade}/{lesson}")
    public String showCategorizedPage(@PathVariable Long id, @PathVariable String grade, @PathVariable String lesson, Model model) {
        Teacher teacher = teacherService.findById(id);
        if (teacher != null) {
            List<Course> courses = courseService.getCourseByGradeAndLesson(grade, lesson);
            model.addAttribute("courses", courses);
            model.addAttribute("teacher", teacher);
            return "categorized_teacher_homepage";
        } else {
            return "redirect:/error";
        }
    }

    @PostMapping("/user/{id}/search")
    public String searchByWord(@PathVariable Long id, @RequestParam("searchedWord") String searchedWord, Model model) {
        Teacher teacher = teacherService.findById(id);
        List<Course> allCourses = courseService.getAllCourses();
        List<Course> filteredCourses = allCourses.stream()
                .filter(course -> course.getDescription().toUpperCase().contains(searchedWord.toUpperCase())
                        || course.getName().toUpperCase().contains(searchedWord.toUpperCase())
                        || course.getLesson().toUpperCase().contains(searchedWord.toUpperCase()))
                .toList();
        model.addAttribute("courses", filteredCourses);
        model.addAttribute("teacher", teacher);
        return "categorized_teacher_homepage";
    }
}

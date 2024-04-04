package com.cobanoglu.denemebrain.controller;

import com.cobanoglu.denemebrain.entity.Course;
import com.cobanoglu.denemebrain.entity.TakenCourse;
import com.cobanoglu.denemebrain.entity.User;
import com.cobanoglu.denemebrain.service.CourseService;
import com.cobanoglu.denemebrain.service.TakenCourseService;
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
@RequestMapping("/user/home")
public class UserProfileController {
    private UserService userService;
    private TakenCourseService takenCourseService;
    private CourseService courseService;

    @Autowired
    public UserProfileController(UserService userService, TakenCourseService takenCourseService, CourseService courseService) {
        this.userService = userService;
        this.takenCourseService = takenCourseService;
        this.courseService = courseService;
    }

    @GetMapping("/{id}/profile")
    public String showUserProfilePage(@PathVariable Long id, Model model) {
        User user = userService.findById(id);
        List<TakenCourse> takenCourses = takenCourseService.findByUserId(id);


        if (user == null) {
            return "error";
        }

        List<String> courseNames = new ArrayList<>();
        List<String> courseDescriptions = new ArrayList<>();

        // Her bir alınan kurs için ilgili kursun adı ve açıklamasını al
        // Eğer alınan kurslar varsa
        if (!takenCourses.isEmpty()) {
            // Kurs adları ve açıklamalarını saklamak için liste oluştur


            // Her bir alınan kurs için işlem yap
            for (TakenCourse takenCourse : takenCourses) {
                // Verilen takenCourse'un course nesnesini al
                Course course = takenCourse.getCourse();
                if (course != null) {
                    // Kurs adını ve açıklamasını listelere ekle
                    courseNames.add(course.getName());
                    courseDescriptions.add(course.getDescription());
                }
            }

        }
        model.addAttribute("firstName", user.getUsername());
        model.addAttribute("lastName", user.getSurname());
        model.addAttribute("email", user.getEmail());
        model.addAttribute("password", user.getPassword());
        model.addAttribute("personalInfo", user.getInformation());
        model.addAttribute("takenCourses", takenCourses);




        return "user_profile";

    }
}







package com.cobanoglu.denemebrain.controller;

import com.cobanoglu.denemebrain.entity.Course;
import com.cobanoglu.denemebrain.service.CourseService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("/teacher/user")
public class CourseEditController {

    private final CourseService courseService;

    public CourseEditController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping("/{id}/course/edit/{courseId}")
    public String showCourseEditPage(@PathVariable("id") Long id,
                                     @PathVariable("courseId") Long courseId,
                                     Model model) {
        Optional<Course> optionalCourse = courseService.getCourseById(courseId);
        if (optionalCourse.isPresent()) {
            Course course = optionalCourse.get();
            model.addAttribute("course", course);
            Map<String, List<String>> availableHoursMap = courseService.parseAvailableHours(course.getAvailableHours());
            model.addAttribute("availableHoursMap", availableHoursMap);
        } else {
            model.addAttribute("course", new Course());
        }
        return "teacher_course_edit";
    }

    @PostMapping("/{id}/course/edit/{courseId}")
    public String updateCourse(@PathVariable("id") Long id,
                               @PathVariable("courseId") Long courseId,
                               @RequestParam("course_name") String courseName,
                               @RequestParam("course_description") String courseDescription,
                               @RequestParam("course_price") int coursePrice,
                               @RequestParam("course_availabletimes") String courseAvailableTimes,
                               @RequestParam("course_availablehours") String courseAvailableHours,
                               Model model) {

        if (courseName.isBlank() || courseDescription.isBlank() || coursePrice <= 0) {
            model.addAttribute("errorMessage", "Kurs adı, açıklaması ve fiyatı boş bırakılamaz.");
            return "teacher_course_edit";
        }

        Optional<Course> optionalCourse = courseService.getCourseById(courseId);
        if (optionalCourse.isPresent()) {
            Course course = optionalCourse.get();

            course.setName(courseName);
            course.setDescription(courseDescription);
            course.setPrice(coursePrice);
            course.setAvailableTimes(courseAvailableTimes);
            course.setAvailableHours(courseAvailableHours);

            courseService.save(course);

            model.addAttribute("successMessage", "Kurs başarıyla güncellendi.");
        } else {
            model.addAttribute("errorMessage", "Kurs bulunamadı.");
        }

        return "redirect:/teacher/user/" + id + "/mycourses";
    }
}

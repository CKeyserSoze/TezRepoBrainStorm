package com.cobanoglu.denemebrain.controller;

import com.cobanoglu.denemebrain.entity.Course;
import com.cobanoglu.denemebrain.entity.CourseSchedule;
import com.cobanoglu.denemebrain.entity.Teacher;
import com.cobanoglu.denemebrain.service.CourseScheduleService;
import com.cobanoglu.denemebrain.service.CourseService;
import com.cobanoglu.denemebrain.service.TeacherService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.StringJoiner;

@Controller
@RequestMapping("/teacher/user")
public class TeacherAddCourseController {

    private final CourseService courseService;
    private final TeacherService teacherService;
    private final CourseScheduleService courseScheduleService;

    public TeacherAddCourseController(CourseService courseService, TeacherService teacherService, CourseScheduleService courseScheduleService) {
        this.courseService = courseService;
        this.teacherService = teacherService;
        this.courseScheduleService = courseScheduleService;
    }

    @GetMapping("/{id}/addcourse")
    public String showAddCourseForm(@PathVariable("id") Long id, Model model) {
        model.addAttribute("id", id);
        return "teacher_addcourse";
    }

    @PostMapping("/{id}/addcourse")
    public String addCourse(@PathVariable("id") Long teacherId,
                            @RequestParam("courseName") String courseName,
                            @RequestParam("description") String description,
                            @RequestParam("price") String price,
                            @RequestParam("classLevel") String classLevel,
                            @RequestParam("courseSubject") String courseSubject,
                            @RequestParam("availableTimes") String availableTimes,
                            @RequestParam("availableTimesAsHours") String availableTimesAsHours,
                            Model model) {

        // Kurs bilgilerinin doğruluğunu kontrol et
        if (courseName.isEmpty() || description.isEmpty() || price.isEmpty()) {
            model.addAttribute("errorMessage", "Lütfen tüm alanları doldurun.");
            return "teacher_addcourse";
        }

        // Öğretmenin varlığını doğrula
        Teacher teacher = teacherService.findById(teacherId);
        if (teacher == null) {
            model.addAttribute("errorMessage", "Öğretmen bulunamadı.");
            return "teacher_addcourse";
        }

        // Kursu kaydet
        int parsedPrice = Integer.parseInt(price);
        Course course = new Course(courseName, description, parsedPrice, "/static/images/course-image2.jpg", classLevel, courseSubject, teacher);
        courseService.save(course);

        // Müsait zamanları işle ve formatla
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d-M-yyyy");
        String[] dates = availableTimes.split(", ");
        String[] hoursArray = availableTimesAsHours.split(", ");

        for (String date : dates) {
            LocalDate formattedDate = LocalDate.parse(date, formatter);
            StringJoiner formattedHours = new StringJoiner(",");
            for (String hour : hoursArray) {
                formattedHours.add(hour);
            }
            CourseSchedule courseSchedule = new CourseSchedule(course, formattedDate.toString(), formattedHours.toString());
            courseScheduleService.save(courseSchedule);
        }

        // Başarılı mesajı ekle ve öğretmenin profil sayfasına yönlendir
        model.addAttribute("successMessage", "Kurs başarıyla eklendi.");
        return "redirect:/teacher/user/" + teacherId;
    }
}

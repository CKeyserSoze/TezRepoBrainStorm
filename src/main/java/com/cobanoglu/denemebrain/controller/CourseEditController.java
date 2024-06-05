package com.cobanoglu.denemebrain.controller;

import com.cobanoglu.denemebrain.entity.Course;
import com.cobanoglu.denemebrain.entity.CourseSchedule;
import com.cobanoglu.denemebrain.service.CourseScheduleService;
import com.cobanoglu.denemebrain.service.CourseService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/teacher/user")
public class CourseEditController {

    private final CourseService courseService;
    private final CourseScheduleService courseScheduleService;

    public CourseEditController(CourseService courseService, CourseScheduleService courseScheduleService) {
        this.courseService = courseService;
        this.courseScheduleService = courseScheduleService;
    }

    @GetMapping("/{id}/course/edit/{courseId}")
    public String showCourseEditPage(@PathVariable("id") Long id,
                                     @PathVariable("courseId") Long courseId,
                                     @RequestParam(value = "selectedDate", required = false) String date,
                                     Model model) {
        Optional<Course> optionalCourse = courseService.getCourseById(courseId);
        if (optionalCourse.isPresent()) {
            Course course = optionalCourse.get();
            model.addAttribute("course", course);

            List<String> availableHours = Arrays.asList("09:00", "10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00", "17:00", "18:00", "19:00", "20:00", "21:00");

            model.addAttribute("availableHours", availableHours);
            model.addAttribute("selectedDate", date);

            if (date != null && !date.isEmpty()) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                LocalDate formattedDate = LocalDate.parse(date, formatter);
                List<CourseSchedule> schedules = courseScheduleService.getSchedulesByCourseAndDate(courseId, formattedDate.toString());

                for(CourseSchedule schedule : schedules)
                {
                    String temp = schedule.getHours();
                    temp = temp.replace("[","");
                    temp = temp.replace("]","");
                    schedule.setHours(temp);
                }

                List<String> selectedUnavailableHours = schedules.stream()
                        .flatMap(schedule -> Arrays.stream(schedule.getHours().split(",")))
                        .collect(Collectors.toList());

                model.addAttribute("selectedUnavailableHours", selectedUnavailableHours);
            }
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
                               @RequestParam("selectedDate") String selectedDate,
                               @RequestParam("course_availablehours") String courseAvailableHours,
                               Model model) {

        courseAvailableHours = sortHours(courseAvailableHours);

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
            courseService.save(course);

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            LocalDate formattedDate = LocalDate.parse(selectedDate, formatter);

            List<CourseSchedule> existingSchedules = courseScheduleService.getSchedulesByCourseAndDate(courseId, formattedDate.toString());

            String cleanedHours = Arrays.stream(courseAvailableHours.split(","))
                    .map(String::trim)
                    .filter(h -> !h.contains("${"))
                    .collect(Collectors.joining(","));

            if (!existingSchedules.isEmpty()) {
                CourseSchedule existingSchedule = existingSchedules.get(0);
                existingSchedule.setHours(cleanedHours);
                courseScheduleService.save(existingSchedule);
            } else {
                CourseSchedule newSchedule = new CourseSchedule(course, formattedDate.toString(), cleanedHours);
                courseScheduleService.save(newSchedule);
            }

            model.addAttribute("successMessage", "Kurs başarıyla güncellendi.");
        } else {
            model.addAttribute("errorMessage", "Kurs bulunamadı.");
        }

        return "redirect:/teacher/user/" + id + "/mycourses";
    }

    public static String sortHours(String hoursString) {
        // Remove brackets and split by commas
        String cleanedString = hoursString.replaceAll("[\\[\\]]", "");
        List<String> hoursList = Arrays.asList(cleanedString.split(","));

        // Sort the hours
        List<String> sortedList = hoursList.stream()
                .map(String::trim)
                .sorted()
                .collect(Collectors.toList());

        // Join the sorted list back into a string
        return String.join(",", sortedList);
    }
}

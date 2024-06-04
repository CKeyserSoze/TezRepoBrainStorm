package com.cobanoglu.denemebrain.controller;

import com.cobanoglu.denemebrain.entity.Comments;
import com.cobanoglu.denemebrain.entity.Course;
import com.cobanoglu.denemebrain.entity.CourseSchedule;
import com.cobanoglu.denemebrain.service.CommentsService;
import com.cobanoglu.denemebrain.service.CourseScheduleService;
import com.cobanoglu.denemebrain.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/user/home")
public class ShowCourseController {

    private final CourseService courseService;
    private final CommentsService commentsService;
    private final CourseScheduleService courseScheduleService;

    @Autowired
    public ShowCourseController(CourseService courseService,CommentsService commentsService,CourseScheduleService courseScheduleService) {
        this.courseService = courseService;
        this.commentsService = commentsService;
        this.courseScheduleService = courseScheduleService;
    }

    @GetMapping("/{id}/course/{courseId}")
    public String showCoursePage(@PathVariable("id") Long userId,
                                 @PathVariable("courseId") Long courseId,
                                 @RequestParam(value = "date", required = false) String date,
                                 Model model){
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

        Course course = courseService.findById(courseId);
        List<Comments>  commentsList = commentsService.getCommentsByCourseId(courseId);
        model.addAttribute("courses", course);
        model.addAttribute("comments",commentsList);

        return "show_course";
    }
}

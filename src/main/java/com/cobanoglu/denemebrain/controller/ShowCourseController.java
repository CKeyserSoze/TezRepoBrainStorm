package com.cobanoglu.denemebrain.controller;

import com.cobanoglu.denemebrain.entity.Comments;
import com.cobanoglu.denemebrain.entity.Course;
import com.cobanoglu.denemebrain.service.CommentsService;
import com.cobanoglu.denemebrain.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/user/home")
public class ShowCourseController {

    private final CourseService courseService;
    private final CommentsService commentsService;

    @Autowired
    public ShowCourseController(CourseService courseService,CommentsService commentsService) {
        this.courseService = courseService;
        this.commentsService = commentsService;
    }

    @GetMapping("/{id}/course/{courseId}")
    public String showCoursePage(@PathVariable("id") Long userId,
                                 @PathVariable("courseId") Long courseId,
                                 Model model){

        Course course = courseService.findById(courseId);
        List<Comments>  commentsList = commentsService.getCommentsByCourseId(courseId);
        model.addAttribute("courses", course);
        model.addAttribute("comments",commentsList);

        return "show_course";
    }
}

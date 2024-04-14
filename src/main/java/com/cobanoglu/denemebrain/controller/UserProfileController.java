package com.cobanoglu.denemebrain.controller;

import com.cobanoglu.denemebrain.entity.*;
import com.cobanoglu.denemebrain.service.CommentsService;
import com.cobanoglu.denemebrain.service.CourseService;
import com.cobanoglu.denemebrain.service.TakenCourseService;
import com.cobanoglu.denemebrain.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/user/home")
public class UserProfileController {
    private UserService userService;
    private TakenCourseService takenCourseService;
    private CourseService courseService;
    private CommentsService commentsService;

    @Autowired
    public UserProfileController(UserService userService,
                                 TakenCourseService takenCourseService,
                                 CourseService courseService,
                                 CommentsService commentsService) {
        this.userService = userService;
        this.takenCourseService = takenCourseService;
        this.courseService = courseService;
        this.commentsService = commentsService;
    }

    @GetMapping("/{id}/profile")
    public String showUserProfilePage(@PathVariable Long id, Model model) {
        User user = userService.findById(id);
        if (user == null) {
            return "error";
        }
        //Kurs daha önce user tarafından puanlandı mı kontrol etmek için yorumları vealınan kursları getirdim.
        //Eğer alınan kursun user_id'si ve course_id'si comments tablosunda varsa daha önce değerlendirmiştir.
        //Değerlendirilen ve değerlendirilmemiş iki farklı listeyle modele yollanacak.
        ArrayList<Course> notRatedCourses = new ArrayList<Course>();
        ArrayList<Course> ratedCourses = new ArrayList<Course>();
        ArrayList<Comments> commentsOfLesson = new ArrayList<>();
        ArrayList<CourseCommentModel> courseComments = new ArrayList<>();

        List<TakenCourse> takenCourses = takenCourseService.findByUserId(id);
        List<Comments> comments = commentsService.getAllComments();

        for(var course : takenCourses)
        {
            boolean flag = false;
            Comments commentToList = new Comments();
            for(var comment : comments)
            {
                if (comment.getUser().getId().equals(course.getUser().getId())
                        && comment.getCourse().getId().equals(course.getCourse().getId())) {
                    flag = true;
                    commentToList=comment;
                    commentsOfLesson.add(comment);
                    break;
                }
            }
            if(flag)
            {
                ratedCourses.add(course.getCourse());
                CourseCommentModel courseComment = new CourseCommentModel();
                courseComment.setCourse(course.getCourse());
                courseComment.setComment(commentToList);
                courseComments.add(courseComment);
            }
            else
            {
                notRatedCourses.add(course.getCourse());
            }
        }



        model.addAttribute("firstName", user.getUsername());
        model.addAttribute("lastName", user.getSurname());
        model.addAttribute("email", user.getEmail());
        model.addAttribute("password", user.getPassword());
        model.addAttribute("personalInfo", user.getInformation());
        model.addAttribute("notRatedCourses", notRatedCourses);
        model.addAttribute("ratedCourses",courseComments);



        return "user_profile";

    }
    @PostMapping("/{id}/profile")
    public String SendComments(@PathVariable Long id,
                               @RequestParam("courseId") Long courseId,
                               @RequestParam("rating") int rating,
                               @RequestParam("comment") String comment,
                               Model model){
        Course course = courseService.findById(courseId);
        User user = userService.getUserById(id);
        List<Comments> comments = commentsService.getAllComments();
        int toplam = rating;
        int commentSayisi = 1;
        for(var commentFromServ : comments)
        {
            if(commentFromServ.getCourse().getId().equals(courseId))
            {
                commentSayisi++;
                toplam +=commentFromServ.getRating();
            }
        }

        float kursunPuani = (float) toplam/commentSayisi;

        Optional<Course> courseFrom = courseService.getCourseById(courseId);
        if(courseFrom.isPresent())
        {
            courseFrom.get().setRating(kursunPuani);
            courseService.updateCourse(courseFrom.get());
        }

        Comments commentToAdd = new Comments();
        commentToAdd.setComment(comment);
        commentToAdd.setCourse(course);
        commentToAdd.setUser(user);
        commentToAdd.setRating(rating);

        commentsService.createComments(commentToAdd);

        return "redirect:/user/home/" + id + "/profile";
    }
}







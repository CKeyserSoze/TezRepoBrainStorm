package com.cobanoglu.denemebrain.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/teacher")
public class TeacherWelcomeController {

    @GetMapping("/information")
    public String showTeacherWelcomePage(){

        return "teacher_welcome";
    }
}

package com.cobanoglu.denemebrain.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/home")
public class BrainStormKimdirController {

    @GetMapping("/brainstorm")
    public String showBrainStormPage(){

        return "brainstorm_nedir";
    }

}

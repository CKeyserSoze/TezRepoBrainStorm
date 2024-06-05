package com.cobanoglu.denemebrain.controller;

import com.cobanoglu.denemebrain.service.GoogleMeetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GoogleMeetController {

    @Autowired
    private GoogleMeetService googleMeetService;

    @GetMapping("/create-meet")
    public String createMeet() {
        try {
            return googleMeetService.createGoogleMeetLink();
        } catch (Exception e) {
            e.printStackTrace();
            return "Error creating meeting";
        }
    }
}
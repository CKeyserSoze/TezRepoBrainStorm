package com.cobanoglu.denemebrain.controller;

import com.cobanoglu.denemebrain.entity.Notification;
import com.cobanoglu.denemebrain.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/teacher/user")
public class NotificationController {

    private final NotificationService notificationService;

    @Autowired
    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @GetMapping("/{id}/notifications")
    public String getNotifications(@PathVariable Long id, Model model) {
        List<Notification> notifications = notificationService.getNotificationsForTeacher(id);
        model.addAttribute("notifications", notifications);
        return "notifications";
    }
}

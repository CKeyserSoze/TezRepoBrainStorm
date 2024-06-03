package com.cobanoglu.denemebrain.service;

import com.cobanoglu.denemebrain.entity.Notification;

import java.util.List;

public interface NotificationService {
    List<Notification> getNotificationsForTeacher(Long teacherId);
    void saveNotification(Notification notification);
}

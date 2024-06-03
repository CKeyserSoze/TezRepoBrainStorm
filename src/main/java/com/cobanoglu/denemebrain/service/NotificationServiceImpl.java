package com.cobanoglu.denemebrain.service.impl;

import com.cobanoglu.denemebrain.entity.Notification;
import com.cobanoglu.denemebrain.repository.NotificationRepository;
import com.cobanoglu.denemebrain.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;

    @Autowired
    public NotificationServiceImpl(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    @Override
    public List<Notification> getNotificationsForTeacher(Long teacherId) {
        return notificationRepository.findByTeacher_Id(teacherId);
    }

    @Override
    public void saveNotification(Notification notification) {
        notificationRepository.save(notification);
    }
}

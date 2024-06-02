package com.cobanoglu.denemebrain.service;

import com.cobanoglu.denemebrain.entity.CourseSchedule;

import java.util.List;

public interface CourseScheduleService {
    List<CourseSchedule> getSchedulesByCourseAndDate(Long courseId, String date);
    void save(CourseSchedule courseSchedule);
}

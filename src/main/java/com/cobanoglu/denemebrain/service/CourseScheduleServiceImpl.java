package com.cobanoglu.denemebrain.service;

import com.cobanoglu.denemebrain.entity.CourseSchedule;
import com.cobanoglu.denemebrain.repository.CourseScheduleRepository;
import com.cobanoglu.denemebrain.service.CourseScheduleService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseScheduleServiceImpl implements CourseScheduleService {

    private final CourseScheduleRepository courseScheduleRepository;

    public CourseScheduleServiceImpl(CourseScheduleRepository courseScheduleRepository) {
        this.courseScheduleRepository = courseScheduleRepository;
    }

    @Override
    public List<CourseSchedule> getSchedulesByCourseAndDate(Long courseId, String date) {
        return courseScheduleRepository.findByCourseIdAndDate(courseId, date);
    }

    @Override
    public void save(CourseSchedule courseSchedule) {
        courseScheduleRepository.save(courseSchedule);
    }
}

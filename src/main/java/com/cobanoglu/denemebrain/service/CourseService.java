package com.cobanoglu.denemebrain.service;

import com.cobanoglu.denemebrain.entity.Course;

import java.util.List;
import java.util.Optional;

public interface CourseService {
    List<Course> getAllCourses();
    Optional<Course> getCourseById(Long id);
    Course createCourse(Course course);
    Course updateCourse(Course course);
    void deleteCourseById(Long id);
    void save(Course course);
    Course findById(Long id);
    List<Course> findByTeacherId(Long teacherId);

}

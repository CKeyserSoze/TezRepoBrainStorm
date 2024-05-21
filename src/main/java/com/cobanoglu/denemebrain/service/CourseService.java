package com.cobanoglu.denemebrain.service;

import com.cobanoglu.denemebrain.entity.Course;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface CourseService {
    List<Course> getAllCourses();
    Optional<Course> getCourseById(Long id);
    List<Course> getCourseByGradeAndLesson(String grade, String Lesson);
    List<Course> getCourseByLesson(String Lesson);
    Course createCourse(Course course);
    Course updateCourse(Course course);
    void deleteCourseById(Long id);
    void save(Course course);
    Course findById(Long id);
    List<Course> findByTeacherId(Long teacherId);
    List<Course> getCoursesFilteredBy(String filter);
    List<String> getAvailableHoursForDate(Long courseId, String date);
    Map<String, List<String>> parseAvailableHours(String availableHoursString);
    List<String> getAvailableDates(String availableTimes);
    List<String> getAvailableHours(String availableHours);

}

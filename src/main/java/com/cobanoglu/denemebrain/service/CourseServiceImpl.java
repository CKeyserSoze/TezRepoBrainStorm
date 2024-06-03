package com.cobanoglu.denemebrain.service;

import com.cobanoglu.denemebrain.entity.Course;
import com.cobanoglu.denemebrain.repository.CourseRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CourseServiceImpl implements CourseService{

    private final CourseRepository courseRepository;

    @Autowired
    public CourseServiceImpl(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }
    @Override
    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }
    @Override
    public Optional<Course> getCourseById(Long id) {
        return courseRepository.findById(id);
    }

    @Override
    public List<Course> getCourseByGradeAndLesson(String grade, String Lesson) {return courseRepository.findByGradeAndLesson(grade,Lesson);}

    @Override
    public List<Course> getCourseByLesson(String Lesson) {return courseRepository.findByLesson(Lesson);}

    @Override
    public Course createCourse(Course course) {
        return courseRepository.save(course);
    }
    @Override
    public Course updateCourse(Course course) {
        return courseRepository.save(course);
    }
    @Override
    public void deleteCourseById(Long id) {
        courseRepository.deleteById(id);
    }
    @Override
    public void save(Course course) {
        courseRepository.save(course);
    }
    @Override
    public Course findById(Long id) {
        return courseRepository.findById(id).orElse(null);
    }
    @Override
    public List<Course> findByTeacherId(Long teacherId) {return courseRepository.findByTeacherId(teacherId);}

    @Override
    public List<Course> getCoursesFilteredBy(String filter) {

        if(filter.equals("rating_asc"))
            return courseRepository.findByOrderByRatingAsc();
        else if(filter.equals("rating_desc"))
            return courseRepository.findByOrderByRatingDesc();
        else if(filter.equals("price_asc"))
            return courseRepository.findByOrderByPriceAsc();
        else if(filter.equals("price_desc"))
            return courseRepository.findByOrderByPriceDesc();
        return null;
    }


    @Override
    public List<String> getAvailableHoursForDate(Long courseId, String date) {
        return null;
    }


    @Override
    public Map<String, List<String>> parseAvailableHours(String availableHoursString) {
        Map<String, List<String>> availableHoursMap = new HashMap<>();
        if (availableHoursString != null) {
            String[] dateHoursArray = availableHoursString.split(";");
            for (String dateHours : dateHoursArray) {
                String[] parts = dateHours.split(":");
                if (parts.length == 2) {
                    availableHoursMap.put(parts[0], Arrays.asList(parts[1].split(",")));
                }
            }
        }
        return availableHoursMap;
    }

    @Override
    public List<String> getAvailableDates(String availableTimes) {
        if (availableTimes != null && !availableTimes.isEmpty()) {
            return Arrays.asList(availableTimes.split(";"));
        }
        return List.of();
    }

    @Override
    public List<String> getAvailableHours(String availableHours) {
        if (availableHours != null && !availableHours.isEmpty()) {
            return Arrays.asList(availableHours.split(";"));
        }
        return List.of();
    }


}

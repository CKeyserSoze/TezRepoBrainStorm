package com.cobanoglu.denemebrain.service;

import com.cobanoglu.denemebrain.entity.Course;
import com.cobanoglu.denemebrain.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
        if ("rating_asc".equals(filter)) {
            return courseRepository.findByOrderByRatingAsc();
        } else if ("rating_desc".equals(filter)) {
            return courseRepository.findByOrderByRatingDesc();
        } else if ("price_asc".equals(filter)) {
            return courseRepository.findByOrderByPriceAsc();
        } else if ("price_desc".equals(filter)) {
            return courseRepository.findByOrderByPriceDesc();
        } else {
            // Filtreleme için uygun bir seçenek yoksa, tüm kursları döndür
            return courseRepository.findAll();
        }
    }
}

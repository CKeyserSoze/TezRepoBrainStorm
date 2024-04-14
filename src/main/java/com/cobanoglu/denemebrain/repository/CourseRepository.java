package com.cobanoglu.denemebrain.repository;

import com.cobanoglu.denemebrain.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

    List<Course> findByTeacherId(Long teacherId);
    List<Course> findByGradeAndLesson(String grade, String Lesson);
    List<Course> findByLesson(String Lesson);
    List<Course> findByOrderByRatingAsc();
    List<Course> findByOrderByRatingDesc();
    List<Course> findByOrderByPriceAsc();
    List<Course> findByOrderByPriceDesc();
}

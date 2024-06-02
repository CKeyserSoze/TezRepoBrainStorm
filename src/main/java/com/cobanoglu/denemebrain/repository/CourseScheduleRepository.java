package com.cobanoglu.denemebrain.repository;

import com.cobanoglu.denemebrain.entity.CourseSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseScheduleRepository extends JpaRepository<CourseSchedule, Long> {
    List<CourseSchedule> findByCourseIdAndDate(Long courseId, String date);
}

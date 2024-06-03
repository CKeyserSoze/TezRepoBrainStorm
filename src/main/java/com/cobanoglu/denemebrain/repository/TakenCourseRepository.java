package com.cobanoglu.denemebrain.repository;

import com.cobanoglu.denemebrain.entity.TakenCourse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TakenCourseRepository extends JpaRepository<TakenCourse, Long> {

    List<TakenCourse> findByUserId(Long userId);
    List<TakenCourse> findByCourse_TeacherId(Long teacherId);

}

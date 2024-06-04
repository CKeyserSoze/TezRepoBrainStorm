package com.cobanoglu.denemebrain.service;

import com.cobanoglu.denemebrain.entity.Course;
import com.cobanoglu.denemebrain.entity.TakenCourse;
import com.cobanoglu.denemebrain.repository.TakenCourseRepository;

import java.util.List;


public interface TakenCourseService {

    List<TakenCourse> findByUserId(Long userId);
    TakenCourse SaveTakenCourse(TakenCourse takenCourse);
    void saveTakenCourse(TakenCourse takenCourseToAdd);
}

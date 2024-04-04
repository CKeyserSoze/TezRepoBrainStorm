package com.cobanoglu.denemebrain.service;

import com.cobanoglu.denemebrain.entity.TakenCourse;
import com.cobanoglu.denemebrain.repository.TakenCourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TakenCourseServiceImpl implements TakenCourseService{

    private final TakenCourseRepository takenCourseRepository;

    @Autowired
    public TakenCourseServiceImpl(TakenCourseRepository takenCourseRepository) {
        this.takenCourseRepository = takenCourseRepository;
    }

    @Override
    public List<TakenCourse> findByUserId(Long userId) {
        return  takenCourseRepository.findByUserId(userId);
    }



}

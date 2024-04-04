package com.cobanoglu.denemebrain.service;

import com.cobanoglu.denemebrain.entity.Course;
import com.cobanoglu.denemebrain.entity.Expertise;
import com.cobanoglu.denemebrain.entity.Teacher;

import java.util.List;
import java.util.Optional;

public interface ExpertiseService {
    List<Expertise> getAllExpertises();
    Optional<Expertise> getExpertiseById(Long id);
    Expertise createExpertise(Expertise expertise);
    Expertise updateExpertise(Expertise expertise);
    void deleteExpertiseById(Long id);
    void saveAllExpertise(String expertiseArea, String experience, Teacher teacher);
    Expertise findbyId(Long id);
    List<Expertise> findByTeacherId(Long teacherId);
    void saveExpertise(Expertise expertise);
}

package com.cobanoglu.denemebrain.service;

import com.cobanoglu.denemebrain.entity.Expertise;
import com.cobanoglu.denemebrain.entity.Teacher;
import com.cobanoglu.denemebrain.repository.ExpertiseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ExpertiseServiceImpl implements ExpertiseService{
    private final ExpertiseRepository expertiseRepository;

    @Autowired
    public ExpertiseServiceImpl(ExpertiseRepository expertiseRepository) {
        this.expertiseRepository = expertiseRepository;
    }

    private Teacher teacher;

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    @Override
    public List<Expertise> getAllExpertises() {
        return expertiseRepository.findAll();
    }

    @Override
    public Optional<Expertise> getExpertiseById(Long id) {
        return expertiseRepository.findById(id);
    }

    @Override
    public Expertise createExpertise(Expertise expertise) {
        return expertiseRepository.save(expertise);
    }

    @Override
    public Expertise updateExpertise(Expertise expertise) {
        return expertiseRepository.save(expertise);
    }

    @Override
    public void deleteExpertiseById(Long id) {
        expertiseRepository.deleteById(id);
    }

    @Override
    public void saveAllExpertise(String expertiseArea, String experience, Teacher teacher) {

        Expertise expertise = new Expertise();
        expertise.setArea(expertiseArea);
        expertise.setYearsOfExperience(experience);
        expertise.setTeacher(teacher);
        expertiseRepository.save(expertise);

    }

    @Override
    public Expertise findbyId(Long id) {
        return expertiseRepository.findById(id).orElse(null);
    }

    @Override
    public List<Expertise> findByTeacherId(Long teacherId) {
        return expertiseRepository.findByTeacherId(teacherId);
    }

    @Override
    public void saveExpertise(Expertise expertise) {
        expertiseRepository.save(expertise);
    }


}

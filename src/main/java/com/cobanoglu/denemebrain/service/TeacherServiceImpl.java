package com.cobanoglu.denemebrain.service;

import com.cobanoglu.denemebrain.entity.Teacher;
import com.cobanoglu.denemebrain.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TeacherServiceImpl implements TeacherService{

    private final TeacherRepository teacherRepository;

    @Autowired
    public TeacherServiceImpl(TeacherRepository teacherRepository) {
        this.teacherRepository = teacherRepository;
    }

    @Override
    public List<Teacher> getAllTeachers() {
        return teacherRepository.findAll();
    }

    @Override
    public Optional<Teacher> getTeacherById(Long id) {
        return teacherRepository.findById(id);
    }

    @Override
    public Teacher createTeacher(Teacher teacher) {
        return teacherRepository.save(teacher);
    }

    @Override
    public Teacher updateTeacher(Teacher teacher) {
        return teacherRepository.save(teacher);
    }

    @Override
    public void deleteTeacherById(Long id) {
        teacherRepository.deleteById(id);
    }

    @Override
    public boolean isValidTeacher(String email, String password) {
        Teacher teacher = teacherRepository.findByEmail(email);
        if(teacher != null && teacher.getPassword().equals(password)) {
            return true;
        }
        return false;

    }

    @Override
    public boolean existsByEmail(String email) {
        return teacherRepository.findByEmail(email) != null;
    }

    @Override
    public void saveTeacher(String firstName, String lastName, String email, String password, String education) {
        Teacher teacher = new Teacher();
        teacher.setName(firstName);
        teacher.setSurname(lastName);
        teacher.setEmail(email);
        teacher.setPassword(password);
        teacher.setEducationLevel(education);
        teacherRepository.save(teacher);

    }

    @Override
    public Teacher findByEmail(String username) {
        return teacherRepository.findByEmail(username);
    }

    @Override
    public Teacher findById(Long id) {
        return teacherRepository.findById(id).orElse(null);
    }

    @Override
    public Teacher findLoggedInTeacher() {
            return null;
    }

    @Override
    public Teacher getUserByEmail(String email) {
        return teacherRepository.findByEmail(email);
    }

    @Override
    public void saveTeacherAll(Teacher teacher) {
        teacherRepository.save(teacher);

    }

    @Override
    public void verifyTeacher(String token) {
        Teacher teacher = teacherRepository.findByVerificationToken(token);
        if (teacher != null) {
            teacher.setUsed(true);
            teacherRepository.save(teacher);
        }
    }


}

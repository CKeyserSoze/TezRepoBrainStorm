package com.cobanoglu.denemebrain.service;

import com.cobanoglu.denemebrain.entity.Teacher;
import java.util.List;
import java.util.Optional;

public interface TeacherService {
    List<Teacher> getAllTeachers();
    Optional<Teacher> getTeacherById(Long id);
    Teacher createTeacher(Teacher teacher);
    Teacher updateTeacher(Teacher teacher);
    void deleteTeacherById(Long id);
    boolean isValidTeacher(String email, String password);
    boolean existsByEmail(String email);
    void saveTeacher(String firstName, String lastName, String email, String password, String education);
    Teacher findByEmail(String username);
    Teacher findById(Long id);
    Teacher findLoggedInTeacher();
    Teacher getUserByEmail(String email);
    void saveTeacherAll(Teacher teacher);
    void verifyTeacher(String token);
}

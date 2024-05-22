package com.cobanoglu.denemebrain.repository;

import com.cobanoglu.denemebrain.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Long> {
    Teacher findByEmail(String username);
    Optional<Teacher> findById(Long id);
    Teacher findByVerificationToken(String token);

    // Özel sorguları veya metotları buraya ekleyebilirsiniz

}

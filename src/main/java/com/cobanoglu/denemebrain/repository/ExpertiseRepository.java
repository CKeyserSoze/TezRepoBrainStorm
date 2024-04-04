package com.cobanoglu.denemebrain.repository;

import com.cobanoglu.denemebrain.entity.Expertise;
import com.cobanoglu.denemebrain.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExpertiseRepository extends JpaRepository<Expertise, Long> {

    Expertise save(Expertise expertise);

    List<Expertise> findByTeacherId(Long teacherId);
}

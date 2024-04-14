package com.cobanoglu.denemebrain.repository;

import com.cobanoglu.denemebrain.entity.Comments;
import com.cobanoglu.denemebrain.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentsRepository extends JpaRepository <Comments, Long> {
    List<Comments> findByCourseId(Long courseId);
}

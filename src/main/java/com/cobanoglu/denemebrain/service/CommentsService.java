package com.cobanoglu.denemebrain.service;

import com.cobanoglu.denemebrain.entity.Comments;

import java.util.List;
import java.util.Optional;

public interface CommentsService {
    List<Comments> getAllComments();
    Optional<Comments> getCommentsById(Long id);
    List<Comments> getCommentsByCourseId(Long courseId);
    Comments createComments(Comments comments);
    Comments updateComments(Comments comments);
    void deleteCommentsById(Long id);
}

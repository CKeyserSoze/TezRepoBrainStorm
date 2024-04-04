package com.cobanoglu.denemebrain.service;

import com.cobanoglu.denemebrain.entity.Comments;
import com.cobanoglu.denemebrain.repository.CommentsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommentsServiceImpl implements CommentsService {

    private final CommentsRepository commentsRepository;

    @Autowired
    public CommentsServiceImpl(CommentsRepository commentsRepository) {
        this.commentsRepository = commentsRepository;
    }

    @Override
    public List<Comments> getAllComments() {
        return commentsRepository.findAll();
    }

    @Override
    public Optional<Comments> getCommentsById(Long id) {
        return commentsRepository.findById(id);
    }

    @Override
    public Comments createComments(Comments comments) {
        return commentsRepository.save(comments);
    }

    @Override
    public Comments updateComments(Comments comments) {
        return commentsRepository.save(comments);
    }

    @Override
    public void deleteCommentsById(Long id) {
        commentsRepository.deleteById(id);
    }
}

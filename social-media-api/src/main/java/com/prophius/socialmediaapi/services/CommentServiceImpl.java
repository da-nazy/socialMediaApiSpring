package com.prophius.socialmediaapi.services;

import com.prophius.socialmediaapi.domain.Comment;
import com.prophius.socialmediaapi.exception.AuthException;
import com.prophius.socialmediaapi.exception.ResourceNotFoundException;
import com.prophius.socialmediaapi.repositories.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
@Transactional
public class CommentServiceImpl implements CommentService{
    @Autowired
    CommentRepository commentRepository;
    @Override
    public Comment createComment(Integer postId,Integer userId, String content, String creationDate) throws AuthException {
        int commentId=commentRepository.createComment(postId,userId,content,creationDate);
        Comment comment=getComment(commentId,userId);
        return comment;
    }

    @Override
    public void removeComment(Integer commentId, Integer userId) throws ResourceNotFoundException {
      commentRepository.removeComment(commentId,userId);
    }

    @Override
    public Comment getComment(Integer commentId, Integer userId) throws ResourceNotFoundException {
        return commentRepository.getComment(commentId,userId);
    }

    @Override
    public List<Comment> getComments(Integer userId) throws ResourceNotFoundException {
        return commentRepository.getComments(userId);
    }

    @Override
    public void updateComment(Integer commentId, Integer userId, String content, String creationDate) throws ResourceNotFoundException {
      commentRepository.updateComment(commentId,userId,content,creationDate);
    }
}

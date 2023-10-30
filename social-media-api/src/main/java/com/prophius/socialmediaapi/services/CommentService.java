package com.prophius.socialmediaapi.services;

import com.prophius.socialmediaapi.domain.Comment;
import com.prophius.socialmediaapi.exception.AuthException;
import com.prophius.socialmediaapi.exception.ResourceNotFoundException;

import java.util.List;

public interface CommentService {
    Comment createComment( Integer postId,Integer userId, String content, String creationDate) throws AuthException;
    void removeComment(Integer commentId,Integer userId) throws ResourceNotFoundException;
    Comment getComment(Integer commentId, Integer userId) throws ResourceNotFoundException;
    List<Comment> getComments(Integer userId) throws ResourceNotFoundException;
    void updateComment(Integer commentId, Integer userId, String content, String creationDate) throws ResourceNotFoundException;
}

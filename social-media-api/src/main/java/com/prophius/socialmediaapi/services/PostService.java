package com.prophius.socialmediaapi.services;

import com.prophius.socialmediaapi.domain.Post;
import com.prophius.socialmediaapi.dto.PostResponse;
import com.prophius.socialmediaapi.exception.AuthException;
import com.prophius.socialmediaapi.exception.BadRequestException;
import com.prophius.socialmediaapi.exception.ResourceNotFoundException;

import java.util.ArrayList;
import java.util.List;

public interface PostService {

    void likePost(Integer postId,Integer userId) throws ResourceNotFoundException;
    Post getPost(Integer postId,Integer userId) throws ResourceNotFoundException;
    PostResponse getPosts(Integer userId, Integer pageNo, Integer pageSize) throws ResourceNotFoundException;

    Post create (Integer userId, String title, String content, String creationDate,Integer likeCount) throws AuthException;
    void removePost(Integer postId,Integer userId) throws ResourceNotFoundException;
    void updatePost(Integer postId, Integer userId,String title, String content, String creationDate,Integer likeCount) throws ResourceNotFoundException;
}

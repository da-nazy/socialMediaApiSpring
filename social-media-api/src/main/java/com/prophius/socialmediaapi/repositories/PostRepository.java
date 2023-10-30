package com.prophius.socialmediaapi.repositories;

import com.prophius.socialmediaapi.domain.Post;
import com.prophius.socialmediaapi.exception.AuthException;
import com.prophius.socialmediaapi.exception.ResourceNotFoundException;
import org.springframework.data.domain.Page;

import java.util.List;

public interface PostRepository {
    Integer create ( Integer userId, String title, String content, String creationDate,Integer likeCount) throws AuthException;
    void removePost(Integer postId,Integer userId) throws ResourceNotFoundException;
    void likePost(Integer postId,Integer userId) throws ResourceNotFoundException;
    Post getPost(Integer postId,Integer userId) throws ResourceNotFoundException;

   // List<Post> getPosts(Integer userId) throws ResourceNotFoundException;
    Page<Post> getPosts(Integer userId, int page, int size) throws ResourceNotFoundException;
    void updatePost(Integer postId, Integer userId,String title, String content, String creationDate,Integer likeCount) throws ResourceNotFoundException;
}

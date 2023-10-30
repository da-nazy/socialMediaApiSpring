package com.prophius.socialmediaapi.services;

import com.prophius.socialmediaapi.domain.Post;
import com.prophius.socialmediaapi.exception.AuthException;
import com.prophius.socialmediaapi.exception.ResourceNotFoundException;
import com.prophius.socialmediaapi.repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class PostServiceImpl implements  PostService{
    @Autowired
    PostRepository postRepository;
    @Override
    public void likePost(Integer postId, Integer userId,Integer likeCount) throws ResourceNotFoundException {
        postRepository.likePost(postId,userId,likeCount);
    }

    @Override
    public Post getPost(Integer postId, Integer userId) throws ResourceNotFoundException {
     return postRepository.getPost(postId,userId);
    }

    @Override
    public List<Post> getPosts(Integer userId) throws ResourceNotFoundException {
        return postRepository.getPosts(userId);
    }

    @Override
    public Post create(Integer userId, String title, String content, String creationDate, Integer likeCount) throws AuthException {
        int postId=postRepository.create(userId,title,content,creationDate,likeCount);
        return postRepository.getPost(postId,userId);
    }

    @Override
    public void removePost(Integer postId, Integer userId) throws ResourceNotFoundException {
       postRepository.removePost(postId,userId);
    }

    @Override
    public void updatePost(Integer postId, Integer userId, String title, String content,String creationDate,Integer likeCount) throws ResourceNotFoundException {
        postRepository.updatePost(postId,userId,title,content,creationDate,likeCount);
    }
}

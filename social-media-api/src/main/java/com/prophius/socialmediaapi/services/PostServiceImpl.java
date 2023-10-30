package com.prophius.socialmediaapi.services;

import com.prophius.socialmediaapi.domain.Post;
import com.prophius.socialmediaapi.dto.PostResponse;
import com.prophius.socialmediaapi.exception.AuthException;
import com.prophius.socialmediaapi.exception.ResourceNotFoundException;
import com.prophius.socialmediaapi.repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.awt.print.Pageable;
import java.util.List;

@Service
@Transactional
public class PostServiceImpl implements  PostService{
    @Autowired
    PostRepository postRepository;
    @Override
    public void likePost(Integer postId, Integer userId) throws ResourceNotFoundException {
        postRepository.likePost(postId,userId);
    }

    @Override
    public Post getPost(Integer postId, Integer userId) throws ResourceNotFoundException {
     return postRepository.getPost(postId,userId);
    }

    @Override
    public PostResponse getPosts(Integer userId, Integer pageNo, Integer pageSize) throws ResourceNotFoundException {
        Page<Post> posts=postRepository.getPosts(userId,pageNo,pageSize);
        PostResponse response=new PostResponse();
         response.setContent(posts.getContent());
         response.setLast(posts.isLast());
          long totalElement= (long) posts.getTotalElements();
          response.setTotalElement(totalElement);
          response.setTotalPages(posts.getTotalPages());
          response.setPageNo(pageNo);
          response.setPageSize(pageSize);

        return response;
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

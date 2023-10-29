package com.prophius.socialmediaapi.resources;

import com.prophius.socialmediaapi.domain.Post;
import com.prophius.socialmediaapi.domain.User;
import com.prophius.socialmediaapi.services.PostService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/posts")
public class PostResources {
  @Autowired
  PostService postService;
    @PostMapping("")
    public ResponseEntity<Post> createPost(@RequestBody Map<String,Object> postMap){
        String content=(String) postMap.get("content");
        String title=(String) postMap.get("title");
        Integer userId=(Integer) postMap.get("userId");
        Integer likeCount=(Integer) postMap.get("likeCount");
        System.out.println(likeCount);
        long  creationDate=(long) postMap.get("creationDate");
        Post post=postService.create(userId,title,content,creationDate,likeCount);
        return new ResponseEntity<>(post, HttpStatus.OK);
    }
    @GetMapping("/{postId}")
    public ResponseEntity<Post> getPost(HttpServletRequest request, @PathVariable("postId") Integer postId){
        int userId=(Integer) request.getAttribute("userId");
        Post post=postService.getPost(postId,userId);
        return new ResponseEntity<>(post, HttpStatus.OK);
    }

    @GetMapping("")
    public ResponseEntity<List<Post>> getPosts(HttpServletRequest request, @PathVariable("postId") Integer postId){
        int userId=(Integer) request.getAttribute("userId");
        List<Post> posts=postService.getPosts(userId);
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }
    @PutMapping("/like/{postId}")
    public ResponseEntity<Map<String,Boolean>> likePost(HttpServletRequest request,@PathVariable("postId") Integer postId,@RequestBody Map<String,Object> postMap){
        int userId=(Integer) request.getAttribute("userId");
        Integer likeCount=(Integer) postMap.get("likeCount");
        postService.likePost(postId,userId,likeCount);
        Map<String,Boolean> map=new HashMap<>();
        map.put("success",true);
        return new ResponseEntity<>(map,HttpStatus.OK);
    }

    @PutMapping("/{postId}")
    public ResponseEntity<Map<String,Boolean>> updatePost(HttpServletRequest request,@PathVariable("postId") Integer postId,@RequestBody Map<String,Object> postMap){
        int userId=(Integer) request.getAttribute("userId");
        String title=(String) postMap.get("title");
        String content=(String) postMap.get("content");
        long creationDate=(long) postMap.get("creationDate");
        Integer likeCount=(Integer) postMap.get("likeCount");
        postService.updatePost(postId,userId,title,content,creationDate,likeCount);
        Map<String,Boolean> map=new HashMap<>();
        map.put("success",true);
        return new ResponseEntity<>(map,HttpStatus.OK);
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<Map<String,Boolean>> removePost(HttpServletRequest request,@PathVariable("postId") Integer postId){
        int userId=(Integer) request.getAttribute("userId");
        postService.removePost(postId,userId);
        Map<String,Boolean> map=new HashMap<>();
        map.put("success",true);
        return new ResponseEntity<>(map,HttpStatus.OK);
    }

}
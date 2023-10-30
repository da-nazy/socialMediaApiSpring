package com.prophius.socialmediaapi.resources;

import com.prophius.socialmediaapi.domain.Post;
import com.prophius.socialmediaapi.domain.User;
import com.prophius.socialmediaapi.dto.PostResponse;
import com.prophius.socialmediaapi.services.PostService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.*;

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
        String  creationDate=(String) postMap.get("creationDate");
        String dateIn = convertDateStringToMillis(creationDate);
        Post post=postService.create(userId,title,content,dateIn,0);
        return new ResponseEntity<>(post, HttpStatus.OK);
    }
    @GetMapping("/{postId}")
    public ResponseEntity<Post> getPost(HttpServletRequest request, @PathVariable("postId") Integer postId){
        int userId=(Integer) request.getAttribute("userId");
        Post post=postService.getPost(postId,userId);
        return new ResponseEntity<>(post, HttpStatus.OK);
    }

    @GetMapping("")
    public ResponseEntity<PostResponse> getPosts(HttpServletRequest request,@RequestParam(value="pageNo",defaultValue="0",required=false) int pageNo,
                                                 @RequestParam(value="pageSize",defaultValue="5",required=false) int pageSize){
        int userId=(Integer) request.getAttribute("userId");
        PostResponse posts=postService.getPosts(userId,pageNo,pageSize);
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }
    @PutMapping("/like/{postId}")
    public ResponseEntity<Map<String,Boolean>> likePost(HttpServletRequest request,@PathVariable("postId") Integer postId){
        int userId=(Integer) request.getAttribute("userId");
     //   Integer likeCount=(Integer) postMap.get("likeCount");
        postService.likePost(postId,userId);
        Map<String,Boolean> map=new HashMap<>();
        map.put("success",true);
        return new ResponseEntity<>(map,HttpStatus.OK);
    }

    @PutMapping("/{postId}")
    public ResponseEntity<Map<String,Boolean>> updatePost(HttpServletRequest request,@PathVariable("postId") Integer postId,@RequestBody Map<String,Object> postMap){
        int userId=(Integer) request.getAttribute("userId");
        String title=(String) postMap.get("title");
        String content=(String) postMap.get("content");
        String creationDate=(String) postMap.get("creationDate");
        Integer likeCount=(Integer) postMap.get("likeCount");
        String dateIn = convertDateStringToMillis(creationDate);
        postService.updatePost(postId,userId,title,content,dateIn,likeCount);
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


    private static String convertDateStringToMillis(String dateString) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");

        try {
            Date date = dateFormat.parse(dateString);
            return date.toString();
        } catch (java.text.ParseException e) {
            e.printStackTrace();
             // Return a default value or handle the exception as needed
        }
        return dateString;
    }

}



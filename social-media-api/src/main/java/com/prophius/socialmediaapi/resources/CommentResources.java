package com.prophius.socialmediaapi.resources;

import com.prophius.socialmediaapi.domain.Comment;
import com.prophius.socialmediaapi.services.CommentService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@RestController
@RequestMapping("/api/comments")
public class CommentResources {
    @Autowired
    CommentService commentService;
    @PostMapping("")
    public ResponseEntity<Comment> createComment(HttpServletRequest request,@RequestBody Map<String,Object> commentMap){
        int userId=(Integer) request.getAttribute("userId");
        String content=(String) commentMap.get("content");
        Integer postId=(Integer) commentMap.get("postId");
        String  creationDate=(String) commentMap.get("creationDate");
        String dateIn = convertDateStringToMillis(creationDate);
        Comment comment=commentService.createComment(postId,userId,content,dateIn);
        return new ResponseEntity<>(comment, HttpStatus.OK);
    }
    @GetMapping("/{commentId}")
    public ResponseEntity<Comment> getComment(HttpServletRequest request, @PathVariable("commentId") Integer commentId){
        int userId=(Integer) request.getAttribute("userId");
        Comment comment=commentService.getComment(commentId,userId);
        return new ResponseEntity<>(comment, HttpStatus.OK);
    }

    @GetMapping("")
    public ResponseEntity<List<Comment>> getComments(HttpServletRequest request){
        int userId=(Integer) request.getAttribute("userId");
        List<Comment> comments=commentService.getComments(userId);
        return new ResponseEntity<>(comments, HttpStatus.OK);
    }


    @PutMapping("/{commentId}")
    public ResponseEntity<Map<String,Boolean>> updateComment(HttpServletRequest request,@PathVariable("commentId") Integer commentId,@RequestBody Map<String,Object> postMap){
        int userId=(Integer) request.getAttribute("userId");
        String content=(String) postMap.get("content");
        String creationDate=(String) postMap.get("creationDate");
        String dateIn = convertDateStringToMillis(creationDate);
        commentService.updateComment(commentId,userId,content,dateIn);
        Map<String,Boolean> map=new HashMap<>();
        map.put("success",true);
        return new ResponseEntity<>(map,HttpStatus.OK);
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<Map<String,Boolean>> removeComment(HttpServletRequest request,@PathVariable("commentId") Integer commentId){
        int userId=(Integer) request.getAttribute("userId");
        commentService.removeComment(commentId,userId);
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

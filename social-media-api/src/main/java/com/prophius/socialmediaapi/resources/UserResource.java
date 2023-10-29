package com.prophius.socialmediaapi.resources;

import com.prophius.socialmediaapi.Constants;
import com.prophius.socialmediaapi.domain.User;
import com.prophius.socialmediaapi.services.UserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserResource {

    @Autowired
    UserService userService;

    @PostMapping("/login")
    public ResponseEntity<Map<String,String>> loginUser(@RequestBody Map<String,Object> userMap){
        String email=(String) userMap.get("email");
        String password=(String) userMap.get("password");
        User user=userService.validateUser(email,password);

        return new ResponseEntity<>(generateJWTToken(user),HttpStatus.OK);

    }
    @DeleteMapping("auth/{userId}")
    public ResponseEntity<Map<String,Boolean>> deleteTransaction(HttpServletRequest request,@PathVariable("userId") Integer userId){
        userService.removeUser(userId);
        Map<String,Boolean> map=new HashMap<>();
        map.put("success",true);
        return new ResponseEntity<>(map,HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<Map<String,String>> registerUser(@RequestBody Map<String,Object> userMap){
        String userName=(String) userMap.get("userName");
        String profilePicture=(String) userMap.get("profilePicture");
        String email=(String) userMap.get("email");
        String password=(String) userMap.get("password");
        User user=userService.registerUser(userName,profilePicture,email,password);
        return new ResponseEntity<>(generateJWTToken(user), HttpStatus.OK);
    }

    @PutMapping("auth/{userId}")
    public ResponseEntity<Map<String,Boolean>>updateUser(HttpServletRequest request,@RequestBody User user){
        int userId=(Integer) request.getAttribute("userId");
        userService.updateUser(userId,user.getProfilePicture(),user.getFollowers(),user.getFollowings());
        Map<String,Boolean> map=new HashMap<>();
        map.put("success",true);
        return new ResponseEntity<>(map,HttpStatus.OK);
    }

    @PutMapping("auth/follow/{id}")
    public ResponseEntity<Map<String,Boolean>>updateUserFollower(HttpServletRequest request,@PathVariable("id") Integer id){
        int userId=(Integer) request.getAttribute("userId");
        userService.follow(userId,id);
        Map<String,Boolean> map=new HashMap<>();
        map.put("success",true);
        return new ResponseEntity<>(map,HttpStatus.OK);
    }
    @PutMapping("auth/unfollow/{id}")
    public ResponseEntity<Map<String,Boolean>>unFollowUser(HttpServletRequest request,@PathVariable("id") Integer id){
        int userId=(Integer) request.getAttribute("userId");
        userService.unfollow(userId,id);
        Map<String,Boolean> map=new HashMap<>();
        map.put("success",true);
        return new ResponseEntity<>(map,HttpStatus.OK);
    }




    private Map<String,String> generateJWTToken(User user){
        long timestamp=System.currentTimeMillis();
        String token= Jwts.builder().signWith(SignatureAlgorithm.HS256, Constants.API_SECRET_KEY)
                .setIssuedAt(new Date(timestamp))
                .setExpiration(new Date(timestamp+Constants.TOKEN_VALIDITY))
                .claim("userId",user.getUserId())
                .claim("email",user.getEmail())
                .claim("userName",user.getUserName())
                .claim("profilePicture",user.getProfilePicture())
                .compact();
        Map<String,String> map=new HashMap<>();
        map.put("token",token);
        map.put("user",user.getUserName());
        map.put("userId",user.getUserId().toString());

        return map;
    }
}

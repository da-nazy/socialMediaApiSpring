package com.prophius.socialmediaapi.repositories;

import com.prophius.socialmediaapi.domain.User;
import com.prophius.socialmediaapi.exception.AuthException;
import com.prophius.socialmediaapi.exception.BadRequestException;
import com.prophius.socialmediaapi.exception.ResourceNotFoundException;

import java.util.ArrayList;


public interface UserRepository {
    Integer create(String username, String profilePicture, String email, String password) throws AuthException;
    User findByEmailAndPassword(String email, String password) throws AuthException;
    void update(Integer userId, String profilePicture, ArrayList<String> followers, ArrayList<String>  followings)throws BadRequestException;
    void follow(Integer userId,Integer followerId) throws BadRequestException;
    void unfollow(Integer userId,Integer followerId) throws BadRequestException;
    Integer getCountByEmail(String email);

    User findById(Integer userId);

    void removeById(Integer userId) throws ResourceNotFoundException;

}

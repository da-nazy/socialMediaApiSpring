package com.prophius.socialmediaapi.services;

import com.prophius.socialmediaapi.domain.User;
import com.prophius.socialmediaapi.exception.AuthException;
import com.prophius.socialmediaapi.exception.BadRequestException;
import com.prophius.socialmediaapi.exception.ResourceNotFoundException;

import java.util.ArrayList;


public interface UserService {
    User validateUser(String email,String password) throws AuthException;
    User registerUser(String userName, String profilePicture, String email, String password) throws AuthException;
    void removeUser(Integer userId) throws ResourceNotFoundException;

    void follow(Integer userId,Integer followerId) throws BadRequestException;
    void unfollow(Integer userId,Integer followerId) throws BadRequestException;

    void updateUser(int userId, String profilePicture, ArrayList<String> followers, ArrayList<String> followings);
}

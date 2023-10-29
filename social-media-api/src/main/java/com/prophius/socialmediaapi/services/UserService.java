package com.prophius.socialmediaapi.services;

import com.prophius.socialmediaapi.domain.User;
import com.prophius.socialmediaapi.exception.AuthException;
import com.prophius.socialmediaapi.exception.BadRequestException;

import java.util.ArrayList;

public interface UserService {
    User validateUser(String email,String password) throws AuthException;
    User registerUser(String firstName, String lastName, String email, String password) throws AuthException;
    void updateUser(String profilePicture, ArrayList<Integer> followers, ArrayList<Integer> followings) throws BadRequestException;

}

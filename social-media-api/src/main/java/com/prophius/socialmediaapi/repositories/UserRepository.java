package com.prophius.socialmediaapi.repositories;

import com.prophius.socialmediaapi.domain.User;
import com.prophius.socialmediaapi.exception.AuthException;
import com.prophius.socialmediaapi.exception.BadRequestException;

import java.util.ArrayList;

public interface UserRepository {
    Integer create(String username, String profilePicture, String email, String password) throws AuthException;
    User findByEmailAndPassword(String email, String password) throws AuthException;
    void update(String profilePicture, ArrayList<Integer> followers,ArrayList<Integer> followings)throws BadRequestException;
    Integer getCountByEmail(String email);

    User findById(Integer userId);

}

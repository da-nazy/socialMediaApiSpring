package com.prophius.socialmediaapi.repositories;

import com.prophius.socialmediaapi.domain.User;
import com.prophius.socialmediaapi.exception.AuthException;
import com.prophius.socialmediaapi.exception.BadRequestException;

import java.util.ArrayList;

public class UserRepositoryImpl implements UserRepository{
    @Override
    public Integer create(String username, String profilePicture, String email, String password) throws AuthException {
        return null;
    }

    @Override
    public User findByEmailAndPassword(String email, String password) throws AuthException {
        return null;
    }

    @Override
    public void update(String profilePicture, ArrayList<Integer> followers, ArrayList<Integer> followings) throws BadRequestException {

    }

    @Override
    public Integer getCountByEmail(String email) {
        return null;
    }

    @Override
    public User findById(Integer userId) {
        return null;
    }
}

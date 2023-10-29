package com.prophius.socialmediaapi.services;

import com.prophius.socialmediaapi.domain.User;
import com.prophius.socialmediaapi.exception.AuthException;
import com.prophius.socialmediaapi.exception.BadRequestException;
import com.prophius.socialmediaapi.exception.ResourceNotFoundException;
import com.prophius.socialmediaapi.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.ArrayList;
import java.util.regex.Pattern;

@Service
@Transactional
public class UserServiceImpl implements UserService{
    @Autowired
    UserRepository userRepository;




    @Override
    public User validateUser(String email, String password) throws AuthException {
        if(email!=null)email=email.toLowerCase();
        return userRepository.findByEmailAndPassword(email,password);
    }

    @Override
    public User registerUser(String userName, String profilePicture, String email, String password) throws AuthException {
        Pattern pattern=Pattern.compile("^(.+)@(.+)$");
        if(email!=null)email=email.toLowerCase();
        if(!pattern.matcher(email).matches())
            throw new AuthException("Invalid email format");
        Integer  count=userRepository.getCountByEmail(email);
        if(count>0)
            throw new AuthException("Email already in use");

        Integer userId=userRepository.create(userName,profilePicture,email,password);
        return userRepository.findById(userId);
    }




    @Override
    public void removeUser(Integer userId) throws ResourceNotFoundException {
        userRepository.removeById(userId);
    }

    @Override
    public void follow(Integer userId, Integer followerId) throws BadRequestException {
        userRepository.follow(userId,followerId);
    }

    @Override
    public void unfollow(Integer userId, Integer followerId) throws BadRequestException {
    userRepository.unfollow(userId,followerId);
    }

    @Override
    public void updateUser(int userId, String profilePicture, ArrayList<String> followers, ArrayList<String> followings) {
        userRepository.update(userId,profilePicture,followers,followings);
    }
}

package com.prophius.socialmediaapi.domain;


import java.util.ArrayList;

public class User {
    private Integer userId;
    private String userName;
    private String profilePicture;
    private ArrayList<String> followers;
    private ArrayList<String>  followings;
    private String email;
    private String password;

    public User(Integer userId, String userName, String profilePicture, ArrayList<String>  followers, ArrayList<String>  followings, String email, String password) {
        this.userId = userId;
        this.userName = userName;
        this.profilePicture = profilePicture;
        this.followers = followers;
        this.followings = followings;
        this.email = email;
        this.password = password;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public ArrayList<String>  getFollowers() {
        return followers;
    }

    public void setFollowers(ArrayList<String>  followers) {
        this.followers = followers;
    }

    public ArrayList<String>  getFollowings() {
        return followings;
    }

    public void setFollowings(ArrayList<String>  followings) {
        this.followings = followings;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

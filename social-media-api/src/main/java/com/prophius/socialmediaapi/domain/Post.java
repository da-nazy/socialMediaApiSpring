package com.prophius.socialmediaapi.domain;

public class Post {
private Integer postId;
private Integer userId;
private String title;
private String content;
private String creationDate;
private Integer likeCount;

    public Post(Integer postId, Integer userId, String title, String content, String creationDate, Integer likeCount) {
        this.postId = postId;
        this.userId = userId;
        this.title = title;
        this.content = content;
        this.creationDate = creationDate;
        this.likeCount = likeCount;
    }

    public Integer getPostId() {
        return postId;
    }

    public void setPostId(Integer postId) {
        this.postId = postId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public Integer getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(Integer likeCount) {
        this.likeCount = likeCount;
    }
}

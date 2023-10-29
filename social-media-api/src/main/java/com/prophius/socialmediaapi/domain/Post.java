package com.prophius.socialmediaapi.domain;

public class Post {
private Integer postId;
private Integer userId;
private String title;
private String content;
private long creationDate;

    public Post(Integer postId, Integer userId, String title, String content, long creationDate) {
        this.postId = postId;
        this.userId = userId;
        this.title = title;
        this.content = content;
        this.creationDate = creationDate;
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

    public long getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(long creationDate) {
        this.creationDate = creationDate;
    }
}

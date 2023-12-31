package com.prophius.socialmediaapi.domain;

public class Comment {
    private Integer commentId;
    private Integer userId;
    private Integer postId;

    private String content;
    private String creationDate;

    public Comment(Integer commentId, Integer userId, Integer postId, String content, String creationDate) {
        this.commentId = commentId;
        this.userId = userId;
        this.postId = postId;

        this.content = content;
        this.creationDate = creationDate;
    }

    public Integer getCommentId() {
        return commentId;
    }

    public void setCommentId(Integer commentId) {
        this.commentId = commentId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getPostId() {
        return postId;
    }

    public void setPostId(Integer postId) {
        this.postId = postId;
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
}

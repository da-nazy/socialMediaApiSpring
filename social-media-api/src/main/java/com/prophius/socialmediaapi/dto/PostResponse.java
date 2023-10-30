package com.prophius.socialmediaapi.dto;

import com.prophius.socialmediaapi.domain.Post;

import java.util.List;
import java.util.List;

public class PostResponse {
 private List<Post> content;
 private  Integer pageNo;
 private Integer pageSize;
 private long totalElement;
 private Integer totalPages;
 private boolean last;

    public PostResponse() {

    }

    public PostResponse(List<Post> content, Integer pageNo, Integer pageSize, long totalElement, Integer totalPages, boolean last) {
        this.content = content;
        this.pageNo = pageNo;
        this.pageSize = pageSize;
        this.totalElement = totalElement;
        this.totalPages = totalPages;
        this.last = last;
    }

    public List<Post> getContent() {
        return content;
    }

    public void setContent(List<Post> content) {
        this.content = content;
    }

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public long getTotalElement() {
        return totalElement;
    }

    public void setTotalElement(long totalElement) {
        this.totalElement = totalElement;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    public boolean isLast() {
        return last;
    }

    public void setLast(boolean last) {
        this.last = last;
    }
}

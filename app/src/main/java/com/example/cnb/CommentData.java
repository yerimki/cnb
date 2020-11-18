package com.example.cnb;

public class CommentData {
    private String userNum;
    private String userId;
    private String review;

    public CommentData() {}

    public CommentData(String num, String userId, String review) {
        this.userNum = num;
        this.userId = userId;
        this.review = review;
    }

    public String getUserNum() {
        return this.userNum;
    }

    public String getUserId() {
        return this.userId;
    }

    public String getReview() {
        return this.review;
    }

    public void setUserNum(String userNum) {this.userNum = userNum; }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setReview(String review){
        this.review = review;
    }

}

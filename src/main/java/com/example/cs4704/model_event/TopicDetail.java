package com.example.cs4704.model_event;

import java.util.List;
import java.util.UUID;

public class TopicDetail {
    private Integer pid;
    private UUID uid;
    private Integer mid;
    private String createTime;
    private String nickname;
    private String postTitle;
    private String postData;
    private Integer commentCount;
    private String dealDate;
    private int week;
    private Integer levelCount;
    private List<CommentDetail> commentDetailList;

    public TopicDetail(Integer pid, UUID uid, Integer mid, String createTime,
                       String nickname, String postTitle, String postData, Integer commentCount,
                       String dealDate, int week, Integer levelCount) {
        this.pid = pid;
        this.uid = uid;
        this.mid = mid;
        this.createTime = createTime;
        this.nickname = nickname;
        this.postTitle = postTitle;
        this.postData = postData;
        this.commentCount = commentCount;
        this.dealDate = dealDate;
        this.week =week;
        this.levelCount = levelCount;
    }

    public Integer getLevelCount() {
        return levelCount;
    }

    public void setLevelCount(Integer levelCount) {
        this.levelCount = levelCount;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public UUID getUid() {
        return uid;
    }

    public void setUid(UUID uid) {
        this.uid = uid;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPostTitle() {
        return postTitle;
    }

    public void setPostTitle(String postTitle) {
        this.postTitle = postTitle;
    }

    public String getPostData() {
        return postData;
    }

    public void setPostData(String postData) {
        this.postData = postData;
    }

    public Integer getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(Integer commentCount) {
        this.commentCount = commentCount;
    }

    public void setMotherPostId(Integer motherPostId) {
        motherPostId = motherPostId;
    }

    public List<CommentDetail> getCommentDetailList() {
        return commentDetailList;
    }

    public void setCommentDetailList(List<CommentDetail> commentDetailList) {
        this.commentDetailList = commentDetailList;
    }

    public Integer getMid() {
        return mid;
    }

    public void setMid(Integer mid) {
        this.mid = mid;
    }

    public String getDealDate() {
        return dealDate;
    }

    public void setDealDate(String dealDate) {
        this.dealDate = dealDate;
    }

    @Override
    public String toString() {
        return "TopicDetail{" +
                "pid=" + pid +
                ", uid=" + uid +
                ", mid=" + mid +
                ", createTime=" + createTime +
                ", nickname='" + nickname + '\'' +
                ", postTitle='" + postTitle + '\'' +
                ", postData='" + postData + '\'' +
                ", commentCount=" + commentCount +
                ", dealTime=" + dealDate +
                ", week=" + week +
                ", levelCount=" + levelCount +
                ", commentDetailList=" + commentDetailList +
                '}';
    }

    public int getWeek() {
        return week;
    }

    public void setWeek(int week) {
        this.week = week;
    }
}

package com.example.cs4704.model_event;

import java.util.UUID;

public class Topic {
    private final Integer pid;
    private final UUID uid;
    private final String postTitle;
    private final String postData;
    private final String nickname;
    private final String createTime;
    private final Integer levelCount;
    private final String dealDate;
    private final int week;

    public Topic(Integer pid, UUID uid, String postTitle, String postData, String nickname,
                 String createTime, Integer levelCount, String dealDate, int week) {
        this.pid = pid;
        this.uid = uid;
        this.postTitle = postTitle;
        this.postData = postData;
        this.nickname = nickname;
        this.createTime = createTime;
        this.levelCount = levelCount;
        this.dealDate = dealDate;
        this.week = week;
    }

    public UUID getUid() {
        return uid;
    }

    public String getPostTitle() {
        return postTitle;
    }

    public String getPostData() {
        return postData;
    }

    public String getNickname() {
        return nickname;
    }

    public String getCreateTime() {
        return createTime;
    }

    public Integer getLevelCount() {
        return levelCount;
    }

    public Integer getPid() {
        return pid;
    }

    public String getDealDate() {
        return dealDate;
    }

    public int getWeek() {
        return week;
    }
}

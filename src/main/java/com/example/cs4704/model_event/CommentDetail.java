package com.example.cs4704.model_event;

//    cid INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
//            uid UUID NOT NULL,
//            pid INT NOT NULL,
//            createTime DATE NOT NULL,
//            nickName VARCHAR(200) NOT NULL,
//            comment VARCHAR(800) NOT NULL,
//            userFrom: VARCHAR(200) NOT NULL,
//            userTo: VARCHAR(200) NOT NULL,
//            uidFrom: INT NOT NULL,
//            uidTo: INT NOT NULL

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;

import java.time.LocalDateTime;
import java.util.UUID;

public class CommentDetail {
    private Integer cid;
    private UUID uid;
    private UUID targetUid;
    private Integer pid;
    private Integer mid;
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonFormat(pattern="dd/MM/yyyy hh:mm a")
    private LocalDateTime commentTime;
    private String nickname;
    private String targetNickname;
    private String comment;
    private String postTitle;

    public CommentDetail(Integer cid, UUID uid, UUID targetUid, Integer pid,
                         Integer mid, LocalDateTime commentTime, String nickname,
                         String targetNickname, String comment, String postTitle) {
        this.cid = cid;
        this.uid = uid;
        this.targetUid = targetUid;
        this.pid = pid;
        this.mid = mid;
        this.commentTime = commentTime;
        this.nickname = nickname;
        this.targetNickname = targetNickname;
        this.comment = comment;
        this.postTitle = postTitle;
    }

    public Integer getCid() {
        return cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
    }

    public UUID getUid() {
        return uid;
    }

    public void setUid(UUID uid) {
        this.uid = uid;
    }

    public UUID getTargetUid() {
        return targetUid;
    }

    public void setTargetUid(UUID targetUid) {
        this.targetUid = targetUid;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public LocalDateTime getCommentTime() {
        return commentTime;
    }

    public void setCommentTime(LocalDateTime commentTime) {
        this.commentTime = commentTime;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickName(String nickName) {
        this.nickname = nickname;
    }

    public String getTargetNickname() {
        return targetNickname;
    }

    public void setTargetNickname(String targetNickname) {
        this.targetNickname = targetNickname;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Integer getMid() {
        return mid;
    }

    public void setMid(Integer mid) {
        this.mid = mid;
    }

    public String getPostTitle() {
        return postTitle;
    }

    public void setPostTitle(String postTitle) {
        this.postTitle = postTitle;
    }
}

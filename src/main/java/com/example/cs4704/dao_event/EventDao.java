package com.example.cs4704.dao_event;

import com.example.cs4704.model_event.CommentDetail;
import com.example.cs4704.model_event.TopicDetail;
import com.example.cs4704.model_event.Topic;
import org.springframework.dao.DataAccessException;

import java.sql.Time;
import java.text.ParseException;
import java.util.List;
import java.util.UUID;

public interface EventDao {

    List<Topic> getTopics() throws DataAccessException;

    void insertTopicOrPost(TopicDetail topicDetail) throws DataAccessException, ParseException;

    void deleteTopicOrPost(Integer pid, UUID uid, Integer motherPostId) throws DataAccessException;

    List<TopicDetail> getTopicDetail(int pid) throws DataAccessException;

    List<TopicDetail> getMyTopics(UUID uid) throws DataAccessException;


    CommentDetail insertComment(CommentDetail commentDetail) throws DataAccessException;

    void deleteComment(Integer cid, UUID uid, Integer pid) throws DataAccessException;

    List<CommentDetail> getMyComments(UUID uid) throws DataAccessException;

    List<TopicDetail> getMyReplies(UUID uid) throws DataAccessException;
}

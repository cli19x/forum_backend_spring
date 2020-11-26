package com.example.cs4704.service;

import com.example.cs4704.dao_event.EventDao;
import com.example.cs4704.model_event.CommentDetail;
import com.example.cs4704.model_event.TopicDetail;
import com.example.cs4704.model_event.Topic;
import com.example.cs4704.model_reponse.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class EventService {

    private final EventDao eventDao;
    private ResponseObject responseObject;

    @Autowired
    public EventService(@Qualifier("postgres-event") EventDao eventDao) {
        this.eventDao = eventDao;
    }

    public ResponseObject getTopics() {
        responseObject = new ResponseObject();
        try {
            List<Topic> topicList = eventDao.getTopics();
            responseObject.setMsg("Topics are successfully retrieved");
            responseObject.setObjects(topicList);
        } catch (DataAccessException e) {
            responseObject.setErrMsg(e.getLocalizedMessage());
        }
        return responseObject;
    }

    public ResponseObject insertNewTopicOrPost(TopicDetail topicDetail) {
        responseObject = new ResponseObject();
        try {
            eventDao.insertTopicOrPost(topicDetail);
            responseObject.setMsg("New post is successfully submitted");
        } catch (DataAccessException | ParseException e) {
            responseObject.setErrMsg(e.getLocalizedMessage());
        }
        return responseObject;
    }

    public ResponseObject deleteTopicOrPost(Integer pid, UUID uid, Integer motherPostId) {
        responseObject = new ResponseObject();
        try {
            eventDao.deleteTopicOrPost(pid, uid, motherPostId);
            responseObject.setMsg("Post is successfully deleted");
        } catch (DataAccessException e) {
            responseObject.setErrMsg(e.getLocalizedMessage());
        }
        return responseObject;
    }

    public ResponseObject getTopicDetail(int pid) {
        responseObject = new ResponseObject();
        try {
            List<TopicDetail> detailList = eventDao.getTopicDetail(pid);
            responseObject.setMsg("Topic detail are successfully retrieved");
            responseObject.setObjects(detailList);
        } catch (DataAccessException e) {
            responseObject.setErrMsg(e.getLocalizedMessage());
        }
        return responseObject;
    }

    public ResponseObject getMyTopics(UUID uid) {
        responseObject = new ResponseObject();
        try {
            List<TopicDetail> myList = eventDao.getMyTopics(uid);
            responseObject.setMsg("Topics for user are successfully retrieved");
            responseObject.setObjects(myList);
        } catch (DataAccessException e) {
            responseObject.setErrMsg(e.getLocalizedMessage());
        }
        return responseObject;
    }


    public ResponseObject getMyReplies(UUID uid) {
        responseObject = new ResponseObject();
        try {
            List<TopicDetail> myReplies = eventDao.getMyReplies(uid);
            responseObject.setMsg("Replies for user are successfully retrieved");
            responseObject.setObjects(myReplies);
        } catch (DataAccessException e) {
            responseObject.setErrMsg(e.getLocalizedMessage());
        }
        return responseObject;
    }

    public ResponseObject insertNewComment(CommentDetail commentDetail) {
        responseObject = new ResponseObject();
        try {
            CommentDetail res = eventDao.insertComment(commentDetail);
            responseObject.setObjects(res);
            responseObject.setMsg("New comment is successfully submitted");
        } catch (DataAccessException e) {
            responseObject.setErrMsg(e.getLocalizedMessage());
        }
        return responseObject;
    }

    public ResponseObject deleteComment(Integer cid, UUID uid, Integer pid) {
        responseObject = new ResponseObject();
        try {
            eventDao.deleteComment(cid, uid, pid);
            responseObject.setMsg("Comment is successfully deleted");
        } catch (DataAccessException e) {
            responseObject.setErrMsg(e.getLocalizedMessage());
        }
        return responseObject;
    }

    public ResponseObject getMyComments(UUID uid) {
        responseObject = new ResponseObject();
        try {
            List<CommentDetail> myList = eventDao.getMyComments(uid);
            responseObject.setMsg("Comments for user are successfully retrieved");
            responseObject.setObjects(myList);
        } catch (DataAccessException e) {
            responseObject.setErrMsg(e.getLocalizedMessage());
        }
        return responseObject;
    }

}

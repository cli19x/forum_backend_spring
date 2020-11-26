package com.example.cs4704.controller;

import com.example.cs4704.model_event.CommentDetail;
import com.example.cs4704.model_event.TopicDetail;
import com.example.cs4704.model_reponse.ResponseObject;
import com.example.cs4704.service.EventService;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;

@RestController
@RequestMapping("/user/api/")
@CrossOrigin("*")
public class EventController {

    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping("/topic/index")
    public ResponseObject getTopics() {

          return eventService.getTopics();
    }

    @PostMapping("/topic/submit")
    public ResponseObject postTopicOrPost(@RequestBody TopicDetail topicDetail) {
        System.out.println(topicDetail.toString());
        return eventService.insertNewTopicOrPost(topicDetail);
    }

    //uid, pid, motherId
    @DeleteMapping("/topic/delete")
    public ResponseObject deleteTopicOrPost(@RequestParam(name = "pid") String pid,
                                            @RequestParam(name = "uid") String uid,
                                            @RequestParam(name = "mid") String mid) {
        return eventService.deleteTopicOrPost(Integer.parseInt(pid), UUID.fromString(uid), Integer.parseInt(mid));
    }

    @GetMapping("/topic-detail/{pid}")
    public ResponseObject getTopicDetail(@PathVariable("pid") int pid) {
        return eventService.getTopicDetail(pid);
    }

    @GetMapping("/my-topic/{uid}")
    public ResponseObject getMyTopics(@PathVariable("uid") String uid) {
        return eventService.getMyTopics(UUID.fromString(uid));
    }

    @GetMapping("/my-reply/{uid}")
    public ResponseObject getMyReplies(@PathVariable("uid") String uid) {
        return eventService.getMyReplies(UUID.fromString(uid));
    }

//    cid: undefined,
//    pid: this.topic.pid,
//    mid: +this.mid,
//    comment: this.postText,
//    createTime: dateTime,
//    token: this.currentUser.token,
//    userNickFrom: this.currentUser.nickName,
//    userNickTo: this.topic.nickName,
//    uidFrom: this.currentUser.uid,
//    uidTo: this.topic.uid

    @PostMapping("/comment/submit")
    public ResponseObject postComment(@RequestBody CommentDetail commentDetail) {
        return eventService.insertNewComment(commentDetail);
    }

    @DeleteMapping("/comment/delete")
    public ResponseObject deleteComment(@RequestParam(name = "cid") String cid,
                                        @RequestParam(name = "uid") String uid,
                                        @RequestParam(name = "pid") String pid) {
        return eventService.deleteComment(Integer.parseInt(cid), UUID.fromString(uid), Integer.parseInt(pid));
    }

    @GetMapping("/comment/{uid}")
    public ResponseObject getMyComments(@PathVariable("uid") String uid) {
        return eventService.getMyComments(UUID.fromString(uid));
    }
}

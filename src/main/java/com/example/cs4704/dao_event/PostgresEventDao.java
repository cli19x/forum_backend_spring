package com.example.cs4704.dao_event;

import com.example.cs4704.model_event.CommentDetail;
import com.example.cs4704.model_event.Topic;
import com.example.cs4704.model_event.TopicDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Repository("postgres-event")
public class PostgresEventDao implements EventDao{

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PostgresEventDao(JdbcTemplate jdbcTemplate) {
        super();
        this.jdbcTemplate = jdbcTemplate;
    }


//    private final UUID uid;
//    private final String postTitle;
//    private final String nickName;
//    private final Date createTime;
//    private final Integer commentCount;
    @Override
    public List<Topic> getTopics() throws DataAccessException {
        final String sql = "SELECT * FROM Topic WHERE mid = -1 ORDER BY create_time DESC";
        return jdbcTemplate.query(sql, (resultSet, i) ->
                new Topic(resultSet.getInt("pid"),
                        UUID.fromString(resultSet.getString("uid")),
                        resultSet.getString("post_title"),
                        resultSet.getString("post_data"),
                        resultSet.getString("nickname"),
                        resultSet.getString("create_time"),
                        resultSet.getInt("level_count"),
                        resultSet.getString("deal_date"),
                        resultSet.getInt("week"))
        );
    }


    @Override
    public void insertTopicOrPost(TopicDetail topicDetail) throws DataAccessException, ParseException {
        Timestamp timestamp = Timestamp.valueOf(LocalDateTime.now());
        if (topicDetail.getMid() != -1) {
            updateLevelCount(topicDetail.getMid(), 1);
        }
        final String sql =
                "INSERT INTO Topic (uid, create_time, mid, nickname, post_title, post_data, comment_count, " +
                        "deal_date, week, level_count) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
        ZonedDateTime date = ZonedDateTime.parse(topicDetail.getDealDate());

        Object[] params = {topicDetail.getUid(), timestamp.toInstant().toString(), topicDetail.getMid(),
                topicDetail.getNickname(), topicDetail.getPostTitle(), topicDetail.getPostData(),
                0, date.toString(), date.getDayOfWeek().getValue(), 0};
        jdbcTemplate.update(sql, params);

    }


    @Override
    public void deleteTopicOrPost(Integer pid, UUID uid, Integer mid) throws DataAccessException {
        // delete self
        final String sql = "DELETE FROM Topic WHERE pid = ? AND uid = ?";
        Object[] params = {pid, uid};
        jdbcTemplate.update(sql, params);

        // if first layer, delete replies and comments
        if (mid == -1) {
            deleteRepliesUnder(pid);
            deleteCommentsUnderTopic(mid);
            // update mother (first layer) level count, delete second layer's comment
        } else {
            updateLevelCount(mid, -1);
            deleteCommentsUnderReply(pid);
        }
    }

    private void updateLevelCount(int pid, int number) throws DataAccessException {
        final String sql = "UPDATE Topic SET level_count = level_count + ? WHERE pid = ?";
        Object[] params = {number, pid};
        jdbcTemplate.update(sql, params);
    }


    private void deleteRepliesUnder(int mid) throws DataAccessException {
        final String sql = "DELETE FROM Topic WHERE mid = ?";
        Object[] params = {mid};
        jdbcTemplate.update(sql, params);
    }

    private void deleteCommentsUnderTopic(int mid) throws DataAccessException {
        final String sql = "DELETE FROM Comment WHERE mid = ?";
        Object[] params = {mid};
        jdbcTemplate.update(sql, params);
    }

    private void deleteCommentsUnderReply(int pid) throws DataAccessException {
        final String sql = "DELETE FROM Comment WHERE pid = ?";
        Object[] params = {pid};
        jdbcTemplate.update(sql, params);
    }



    @Override
    public List<TopicDetail> getTopicDetail(int pid) throws DataAccessException {
        final String sql = "SELECT * FROM Topic WHERE pid = ? OR mid = ? ORDER BY create_time";
        Object[] params = {pid, pid};
        List<TopicDetail> topicDetailList =  jdbcTemplate.query(sql, params, (resultSet, i) ->
                new TopicDetail(resultSet.getInt("pid"),
                        UUID.fromString(resultSet.getString("uid")),
                        resultSet.getInt("mid"),
                        resultSet.getString("create_time"),
                        resultSet.getString("nickname"),
                        resultSet.getString("post_title"),
                        resultSet.getString("post_data"),
                        resultSet.getInt("comment_count"),
                        resultSet.getString("deal_date"),
                        resultSet.getInt("week"),
                        resultSet.getInt("level_count")
                )
        );

        if (topicDetailList.isEmpty()) {
            return null;
        }
        List<CommentDetail> commentDetailList = getCommentsUnder(pid);

        Map<Integer, List<CommentDetail>> commentsGroupByPid = commentDetailList.stream()
                .collect(Collectors.groupingBy(CommentDetail::getPid));

        topicDetailList.forEach(e -> e.setCommentDetailList(commentsGroupByPid.get(e.getPid())));

        return topicDetailList;
    }

    private List<CommentDetail> getCommentsUnder(int pid) throws DataAccessException {
        final String sql2 = "SELECT t.*, c.* FROM Topic t JOIN Comment c ON t.pid = c.pid " +
                "WHERE t.pid = ? OR t.mid = ?";
        Object[] params2 = {pid, pid};
        return jdbcTemplate.query(sql2, params2, (resultSet, i) ->
                new CommentDetail(resultSet.getInt("cid"),
                        (resultSet.getString("uid") == null ? null :
                                UUID.fromString(resultSet.getString("uid"))),
                        (resultSet.getString("target_uid") == null ? null :
                                UUID.fromString(resultSet.getString("uid"))),
                        resultSet.getInt("pid"),
                        resultSet.getInt("mid"),
                        resultSet.getTimestamp("comment_time").toLocalDateTime(),
                        resultSet.getString("nickname"),
                        resultSet.getString("target_nickname"),
                        resultSet.getString("comment"),
                        resultSet.getString("post_title")
                )
        );
    }


    @Override
    public List<TopicDetail> getMyTopics(UUID uid) throws DataAccessException {
        final String sql = "SELECT * FROM Topic WHERE uid = ? AND mid = -1 ORDER BY create_time";
        Object[] params = {uid};
        return jdbcTemplate.query(sql, params, (resultSet, i) ->
                new TopicDetail(resultSet.getInt("pid"),
                        UUID.fromString(resultSet.getString("uid")),
                        resultSet.getInt("mid"),
                        resultSet.getString("create_time"),
                        resultSet.getString("nickname"),
                        resultSet.getString("post_title"),
                        resultSet.getString("post_data"),
                        resultSet.getInt("comment_count"),
                        resultSet.getTimestamp("deal_date").toString(),
                        resultSet.getInt("week"),
                        resultSet.getInt("level_count")
                )
        );
    }


    @Override
    public List<TopicDetail> getMyReplies(UUID uid) throws DataAccessException {
        final String sql_reply = "SELECT * FROM Topic WHERE uid = ? AND mid > -1 ORDER BY create_time";
        Object[] params_reply = {uid};
        return jdbcTemplate.query(sql_reply, params_reply, (resultSet, i) ->
                new TopicDetail(resultSet.getInt("pid"),
                        UUID.fromString(resultSet.getString("uid")),
                        resultSet.getInt("mid"),
                        resultSet.getString("create_time"),
                        resultSet.getString("nickname"),
                        "",
                        resultSet.getString("post_data"),
                        resultSet.getInt("comment_count"),
                        resultSet.getTimestamp("deal_date").toString(),
                        resultSet.getInt("week"),
                        resultSet.getInt("level_count")
                )
        );
    }

    @Override
    public CommentDetail insertComment(CommentDetail commentDetail) throws DataAccessException {
        Timestamp timestamp = Timestamp.valueOf(LocalDateTime.now());
        final String sql =
                "INSERT INTO Comment (uid, target_uid, pid, mid, comment_time, nickname, target_nickname, comment, post_title) " +
                        "VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?);";
        Object[] params = {commentDetail.getUid(), commentDetail.getTargetUid(),
                commentDetail.getPid(), commentDetail.getMid(), timestamp, commentDetail.getNickname(),
                commentDetail.getTargetNickname(), commentDetail.getComment(), commentDetail.getPostTitle()};
        jdbcTemplate.update(sql, params);
        updateCommentCount(commentDetail.getPid(), 1);
        return new CommentDetail(-1, commentDetail.getUid(), commentDetail.getTargetUid(),
                commentDetail.getPid(), commentDetail.getMid(), timestamp.toLocalDateTime(), commentDetail.getNickname(),
                commentDetail.getTargetNickname(), commentDetail.getComment(), commentDetail.getPostTitle());
    }

    @Override
    public void deleteComment(Integer cid, UUID uid, Integer pid) throws DataAccessException {
        final String sql = "DELETE FROM Comment WHERE cid = ? AND uid = ? AND pid = ?";
        Object[] params = {cid, uid, pid};
        jdbcTemplate.update(sql, params);
        updateCommentCount(pid, -1);
    }


    private void updateCommentCount(int pid, int number) throws DataAccessException {
        final String sql = "UPDATE Topic SET comment_count = comment_count + ? WHERE pid = ?";
        Object[] params = {number, pid};
        jdbcTemplate.update(sql, params);
    }


    @Override
    public List<CommentDetail> getMyComments(UUID uid) throws DataAccessException {
        final String sql = "SELECT * FROM Comment WHERE uid = ?";
        Object[] params = {uid};
        return jdbcTemplate.query(sql, params, (resultSet, i) ->
                new CommentDetail(resultSet.getInt("cid"),
                        UUID.fromString(resultSet.getString("uid")),
                        UUID.fromString(resultSet.getString("target_uid")),
                        resultSet.getInt("pid"),
                        resultSet.getInt("mid"),
                        resultSet.getTimestamp("comment_time").toLocalDateTime(),
                        resultSet.getString("nickname"),
                        resultSet.getString("target_nickname"),
                        resultSet.getString("comment"),
                        resultSet.getString("post_title")
                )
        );
    }

}

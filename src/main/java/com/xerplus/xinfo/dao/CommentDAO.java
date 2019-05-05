package com.xerplus.xinfo.dao;

import com.xerplus.xinfo.model.Comment;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CommentDAO {
    String TABLE_NAME = " comment ";
    String INSERT_FIELDS = " content, userId, entityId, entityType, createdDate, status ";
    String SELECT_FIELDS = " id," + INSERT_FIELDS;

    // 增加一条评论
    @Insert({"insert into", TABLE_NAME, "(", INSERT_FIELDS,
            ") values (#{content},#{userId},#{entityId},#{entityType},#{createdDate},#{status})"})
    int addComment(Comment comment);


    // 看某个实体多少评论
    @Select({"select", SELECT_FIELDS, "from", TABLE_NAME,
            "where entityId=#{entityId} and entityType=#{entityType} order by id desc"})
    List<Comment> selectByEntity(@Param("entityId")int entityId,
                                 @Param("entityType")int entityType);

    // 评论数量
    @Select({"select count(id) from", TABLE_NAME, "where entityId=#{entityId} and entityType=#{entityType} "})
    int getCommentCount(@Param("entityId")int entityId,
                        @Param("entityType")int entityType);

    //删除评论，把status设为1
    @Update({"update", TABLE_NAME, "set status=#{status} where entityId=#{entityId} and entityType=#{entityType}"})
    void updateStatus(@Param("entityId") int entityId,
                      @Param("entityType") int entityType,
                      @Param("status") int status);

}
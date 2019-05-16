package com.xerplus.xinfo.dao;

import com.xerplus.xinfo.model.News;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface NewsDAO {
    String TABLE_NAME = " news ";
    String INSERT_FIELDS = " title, link, image, likeCount, commentCount, createdDate, userId ";
    String SELECT_FIELDS = " id," + INSERT_FIELDS;

    @Insert({"insert into", TABLE_NAME, "(", INSERT_FIELDS,
            ") values (#{title},#{link},#{image},#{likeCount},#{commentCount},#{createdDate},#{userId})"})
    int addNews(News news);

    @Select({"select", SELECT_FIELDS, "from", TABLE_NAME, "where id=#{id}"})
    News selectById(int id);

    @Select({"select", SELECT_FIELDS, "from", TABLE_NAME, "where userId=#{userid}"})
    List<News> selectByUserId(int userId);

    @Select({"select", SELECT_FIELDS, "from", TABLE_NAME, "order by likeCount desc limit 10"})
    List<News> selectByLike();

    @Select({"select", SELECT_FIELDS, "from", TABLE_NAME, "order by commentCount desc limit 10"})
    List<News> selectByComment();

    @Update({"update", TABLE_NAME, "set commentCount = #{commentCount} where id=#{id}"})
    int updateCommentCount(@Param("id") int id, @Param("commentCount") int commentCount);

    @Update({"update", TABLE_NAME, "set likeCount = #{likeCount} where id=#{id}"})
    int updateLikeCount(@Param("id") int id, @Param("likeCount") int likeCount);

    List<News> selectByUserIdAndOffset(@Param("userId") int userId);
}

package com.xerplus.xinfo.dao;

import com.xerplus.xinfo.model.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserDAO {
    String TABLE_NAME = " user ";
    String INSERT_FIELDS = " name, password, salt, headUrl ";
    String SELECT_FIELDS = " id, name, password, salt, headUrl ";

    @Options(useGeneratedKeys = true, keyProperty = "id")
    @Insert({"insert into", TABLE_NAME, "(", INSERT_FIELDS,
            ") values (#{name},#{password},#{salt},#{headUrl})"})
    int addUser(User user);

    @Select({"select", SELECT_FIELDS, "from", TABLE_NAME, "where id=#{id}"})
    User selectById(int id);

    @Select({"select", SELECT_FIELDS, "from", TABLE_NAME, "where name=#{name}"})
    User selectByName(String name);

    @Update({"update", TABLE_NAME, "set name=#{name},password=#{password} where id=#{id}"})
    void updateNamePassword(User user);

    @Delete({"delect from", TABLE_NAME, "where id=#{id}"})
    void deleteById(int id);

}

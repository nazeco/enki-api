package com.naseko.enkiapi.biz.mapper;

import com.naseko.enkiapi.biz.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper {

    @Select("select * from user")
    @Results({
            @Result(property = "dataJson", column = "data_json")
    })
    List<User> findAll();

    @Select("select * from user where id = #{id}")
    @Results({
            @Result(property = "dataJson", column = "data_json")
    })
    User findById(Long id);

    @Select("select * from user where username = #{Username}")
    @Results({
            @Result(property = "dataJson", column = "data_json")
    })
    User findByUsername(String Username);

    @Select("select * from user where username = #{Username} and password = #{Password}")
    @Results({
            @Result(property = "dataJson", column = "data_json")
    })
    User findByUsernameAndPassword(String Username, String Password);

    @Insert("insert into user(username,password,data_json) values(#{username},#{password},#{dataJson})")
    void add(User user);

    @Update("update user set username=#{username},password=#{password},data_json=#{dataJson} where id=#{id}")
    void update(User user);

    @Delete("delete from user where id=#{id}")
    void delete(Long id);
}

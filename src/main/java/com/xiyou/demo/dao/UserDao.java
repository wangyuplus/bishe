package com.xiyou.demo.dao;


import com.xiyou.demo.model.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

/**
 * 2 * @Author: wangyu
 * 3 * @Date: 2019/7/16 18:51
 * 4
 */
@Mapper
@Repository(value = "userDao")
public interface UserDao {

    @Insert("insert into user (username,password,salt,status) values (#{username},#{password},#{salt},#{status})")
    void insertUser(User user);

    @Select("select * from user where username =#{username}")
    User findUserByUsername(String username);


    @Select("select * from user where uid =#{uid}")
    User findUserById(@Param("uid") Integer uid);
    @Update("update user set status=#{status},password=#{password},vx=#{vx},qq=#{qq},phone=#{phone} where username=#{username}")
    void updateUser(User user);
}

package com.xiyou.demo.dao;

import com.xiyou.demo.model.DingDan;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: wangyu
 * @Date: 2020/4/21 20:26
 */
@Mapper
@Repository(value = "DingDanDao")
public interface DingDanDao {
    @Insert("insert into dingdan (money,username) values (#{money},#{username})")
    void addDingDan(@Param("money") int money,@Param("username") String username);
    @Update("update dingdan set status=1 where did=#{did}")
    void updateStatusByID(int did);
    @Select("select * from dingdan")
    List<DingDan> getDingdan();
    @Update("update dingdan set status=2 where did=#{did}")
    void noagree(int did);
    @Delete("delete from dingdan where did=#{did}")
    void deletedinngdan(int did);
}

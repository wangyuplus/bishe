package com.xiyou.demo.dao;

import com.xiyou.demo.model.Shopping;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

/**
 * @Author: wangyu
 * @Date: 2020/1/30 16:32
 */
@Mapper
@Repository(value = "ShoppingDao")
public interface ShoppingDao {
    @Insert("insert into Shopping (username,gid,sum,pricesum,status) values (#{username},#{gid},#{sum},#{pricesum},#{status})")
    void addShopping(Shopping shopping);

    //更改支付状态
    @Update("update from shopping set status=1 where gid=#{gid}")
    void updateStatus(Integer gid);

    @Delete("delete from shopping where gid=#{gid}")
    void deleteGid(Integer gid);
}

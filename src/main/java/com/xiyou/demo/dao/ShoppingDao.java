package com.xiyou.demo.dao;

import com.xiyou.demo.model.Shopping;
import com.xiyou.demo.model.ShoppingVO;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @Author: wangyu
 * @Date: 2020/1/30 16:32
 * String name;//物品名字
 *     String type;//类型
 *     String price;//价格
 *     int sum;//物品数量
 *     int price;//单价
 *     int status;//状态 0未支付 1已支付
 */
@Mapper
@Repository(value = "ShoppingDao")
public interface ShoppingDao {
    @Insert("insert into shopping (username,gid,sum) values (#{username},#{gid},#{sum})")
    void addShopping(Shopping shopping);

    //更改支付状态
    @Update("update  shopping set status=1 where gid=#{gid}")
    void updateStatus(Integer gid);

    @Delete("delete from shopping where gid=#{gid} and username=#{username}")
    void deleteGid(@Param("gid") Integer gid,@Param("username") String username);

    @Select("select shopping.gid,name,type,price,shopping.sum as shoppingsum,goods.sum as goodssum,goods.path,status from shopping left join goods on shopping.gid=goods.gid where shopping.username=#{username}")
    List<ShoppingVO> getshop(String username);

    @Update("update  shopping set status=1 where username=#{username}")
    void updateStatusByUsername(String username);

    @Select("select gid from shopping where username=#{username}")
    List<Integer> getGid(String username);

    @Select("select sum from shopping where gid=#{gid} and username=#{username}")
    int getSumByGid(@Param("gid") int gid,@Param("username") String username);

    @Update("update  shopping set sum=sum+1 where gid=#{gid} and username =#{username}")
    void updateShopping(@Param("gid") Integer gid, @Param("username") String username);

    @Update("update  shopping set sum=sum-1 where gid=#{gid} and username =#{username}")
    void reduceShopping(@Param("gid")int gid,@Param("username") String username);

    @Update("update  shopping set sum=#{sum} where gid=#{gid} and username =#{username}")
    void updateShoppingSum(@Param("gid") int gid, @Param("sum") int sum, @Param("username") String username);
}

package com.xiyou.demo.dao;

import com.xiyou.demo.model.Goods;
import com.xiyou.demo.model.GoodsVO;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: wangyu
 * @Date: 2020/1/28 14:37
 */
@Mapper
@Repository(value = "GoodsDao")
public interface GoodsDao {
    @Insert("insert into goods (uid,name,price,path,beizhu,sum,type) values (#{uid},#{name},#{price},#{path},#{beizhu},#{sum},#{type})")
    void addGoods(Goods goods);
    @Select("select gid,goods.uid,name,price,path,beizhu,vx,qq,phone,sum,type from goods inner join user where goods.uid=user.uid")
    List<GoodsVO> getGoods();
    @Delete("delete from goods where name=#{name}")
    void deleteGoods();
    @Update("update  from goods set sum=(sum-#{sum2}) where name=#{name}")
    void updateGoodsSum();

    @Select("select max(gid) from goods")
    int findMaxId();
    @Insert("insert into goods (path) values (#{path})")
    void addPath(String path);
    @Select("select path from goods where gid=#{gid}")
    String findPath(int gid);
    @Update("update goods set name=#{name},price=#{price},path=#{path},beizhu=#{beizhu},sum=#{sum},type=#{type} where gid=#{gid}")
    void updateGoodsById(Goods goods);
    @Select("select gid,name,price,path,beizhu,sum,type from goods where uid=#{uid}")
    List<Goods> findGoodsByUid(int uid);
    @Delete("delete from goods where gid=#{gid}")
    void  deleteGoodsById(int gid);
}

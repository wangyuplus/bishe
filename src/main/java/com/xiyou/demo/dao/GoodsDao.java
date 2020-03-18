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
    @Select("select gid,goods.uid,name,price,path,beizhu,vx,qq,phone,sum,type from goods inner join user where goods.uid=user.uid limit ${10*(page-1)},10")
    List<GoodsVO> getGoods(Integer page);
    @Delete("delete from goods where gid=#{did}")
    void deleteGoods();
    @Update("update  goods set sum=(sum-${sum2}) where gid=#{gid}")
    void updateGoodsSum(@Param("sum2") Integer sum2,@Param("gid") Integer gid);

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

    @Select("select gid,goods.uid,name,price,path,beizhu,vx,qq,phone,sum,type from goods inner join user where goods.uid=user.uid and type=#{type} limit ${10*(page-1)},10")
    List<GoodsVO> getGoodsByType(@Param("type") String type, @Param("page") Integer page);
    @Select("select * from goods where gid=#{id}")
    Goods getGoodsById(Integer id);
}

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

    /**
     * 分页 limit ${10*(page-1)},10
     * @param page
     * @return
     */
    @Select("select gid,goods.uid,name,price,path,beizhu,vx,qq,phone,sum,type from goods left join user on goods.uid=user.uid ")
    List<GoodsVO> getGoods(Integer page);


    @Update("update  goods set sum=(sum-${sum2}) where gid=#{gid}")
    void updateGoodsSum(@Param("sum2") Integer sum2,@Param("gid") Integer gid);


    @Update("update goods set name=#{name},price=#{price},path=#{path},beizhu=#{beizhu},sum=#{sum},type=#{type} where gid=#{gid}")
    void updateGoodsById(Goods goods);

    @Select("select gid,name,price,path,beizhu,sum,type from goods where uid=#{uid}")
    List<Goods> findGoodsByUid(int uid);

    @Delete("delete from goods where gid=#{gid}")
    void  deleteGoodsById(int gid);


    @Select("select * from goods where gid=#{id}")
    Goods getGoodsById(Integer id);

    @Select("select type from goods group by type")
    List<String> getGoodsType();

    @Select("select gid,goods.uid,name,price,path,beizhu,vx,qq,phone,sum,type from goods left join user on goods.uid=user.uid where goods.type=#{type}")
    List<GoodsVO> getGoodsByType(@Param("type") String type);
}

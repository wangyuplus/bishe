package com.xiyou.demo.service;

import com.xiyou.demo.dao.GoodsDao;
import com.xiyou.demo.model.Goods;
import com.xiyou.demo.model.GoodsVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: wangyu
 * @Date: 2020/1/28 14:36
 */
@CacheConfig(cacheNames = "goods", cacheManager = "cacheManager")
@Service
public class GoodsService {
    @Autowired
    GoodsDao goodsDao;
    //添加物品
    public void addGoods(Goods goods){
    goodsDao.addGoods(goods);
    }

    @Cacheable()
    public List<GoodsVO> getGoods(Integer page) {
        return goodsDao.getGoods(page);
    }

    //只添加图片
    public void addPath(String path){
        goodsDao.addPath(path);
    }
    //查找最大gid
    public int findMaxId(){
        return goodsDao.findMaxId();
    }
    //根据gid查找path
    public String findPathByGid(int gid){
        return goodsDao.findPath(gid);
    }

    public void updateGoodsById(Goods goods) {
        goodsDao.updateGoodsById(goods);
    }

    public List<Goods> findGoodsByUid(int uid) {
        return goodsDao.findGoodsByUid(uid);
    }

    public void deleteGoodsByGid(int gid) {
        goodsDao.deleteGoodsById(gid);
    }

    public List<GoodsVO> getGoodsByType(@Param("type") String type, @Param("page") Integer page) {
        return goodsDao.getGoodsByType(type,page);
    }

    public void updateGoodsSum(Integer gid, int sum) {
        goodsDao.updateGoodsSum(sum,gid);
    }

    public Goods getGoodsById(Integer id) {
      return   goodsDao.getGoodsById(id);
    }
}

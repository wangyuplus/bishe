package com.xiyou.demo.service;

import com.xiyou.demo.dao.GoodsDao;
import com.xiyou.demo.model.Goods;
import com.xiyou.demo.model.GoodsVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: wangyu
 * @Date: 2020/1/28 14:36
 */
@Service
public class GoodsService {
    @Autowired
    GoodsDao goodsDao;
    //添加物品
    public void addGoods(Goods goods){
    goodsDao.addGoods(goods);
    }

    public List<GoodsVO> getGoods() {
        return goodsDao.getGoods();
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
}

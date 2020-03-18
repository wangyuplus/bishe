package com.xiyou.demo.service;

import com.xiyou.demo.dao.ShoppingDao;
import com.xiyou.demo.model.Shopping;
import com.xiyou.demo.model.ShoppingVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Author: wangyu
 * @Date: 2020/1/30 16:43
 */
@Service
public class ShoppingService {
    @Autowired
    ShoppingDao shoppingDao;
    public void addShopping(Shopping shopping){
        shoppingDao.addShopping(shopping);
    }
    public void updateStatus(Integer gid){
        shoppingDao.updateStatus(gid);
    }
    public void deleteGid(Integer gid,String username){
        shoppingDao.deleteGid(gid,username);
    }

    public List<ShoppingVO> getshop(String username) {
        return shoppingDao.getshop(username);
    }

    public void updateStatusByUsername(String username) {
        shoppingDao.updateStatusByUsername(username);
    }

    public List<Integer> getGid(String username) {
        return shoppingDao.getGid(username);
    }

    public int getSumByGid(int  gid,String username) {
        return shoppingDao.getSumByGid(gid,username);
    }

    public void updateShopping(Integer sum, Integer gid, String username) {
        shoppingDao.updateShopping(sum,gid,username);
    }
}

package com.xiyou.demo.service;

import com.xiyou.demo.dao.ShoppingDao;
import com.xiyou.demo.model.Shopping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public void deleteGid(Integer gid){
        shoppingDao.deleteGid(gid);
    }
}

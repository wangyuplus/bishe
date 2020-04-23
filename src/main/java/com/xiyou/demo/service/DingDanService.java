package com.xiyou.demo.service;

import com.xiyou.demo.dao.DingDanDao;
import com.xiyou.demo.model.DingDan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: wangyu
 * @Date: 2020/4/21 20:26
 */
@Service
public class DingDanService {
    @Autowired
    DingDanDao dingDanDao;

    public void addDingDan(DingDan dingDan) {
        dingDanDao.addDingDan(dingDan.getMoney(), dingDan.getUsername());
    }

    public void updateStatusByID(int did) {
        dingDanDao.updateStatusByID(did);
    }

    public List<DingDan> getDingDan() {
        return dingDanDao.getDingdan();
    }

    public void noagree(int did) {
        dingDanDao.noagree(did);
    }

    public void deletedinngdan(int did) {
        dingDanDao.deletedinngdan(did);
    }
}

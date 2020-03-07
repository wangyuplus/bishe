package com.xiyou.demo.service;

import com.xiyou.demo.dao.EmailDao;
import com.xiyou.demo.model.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 2 * @Author: wangyu
 * 3 * @Date: 2019/7/19 21:14
 * 4
 */
@Service
public class EmailService {
    @Autowired
    EmailDao emailDao;
    public Email findByEmail(String email) {
        return  emailDao.selectByEmail(email);

    }

    public Email findByUid(Integer uid) {
        return emailDao.selectByUid(uid);
    }
}

package com.xiyou.demo.service;

import com.xiyou.demo.dao.TokenDao;
import com.xiyou.demo.model.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * 2 * @Author: wangyu
 * 3 * @Date: 2019/7/19 22:49
 * 4
 */
@Service
public class TokenService {

    @Autowired
    TokenDao tokenDao;
    public Token getToken(String token) {
        return tokenDao.findToken(token);
    }
    /**
     * 每三天执行一次
     */
    @Scheduled(cron = "0 0 0 1/3 * ? *")
    public void deleteExpireToken() {
    tokenDao.deleteTokens();
    }
}

package com.xiyou.demo.controller;

import com.xiyou.demo.model.Token;
import com.xiyou.demo.service.TokenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 2 * @Author: wangyu
 * 3 * @Date: 2019/7/19 22:46
 * 4
 */
@RestController
@CrossOrigin
@RequestMapping(path = "/api")
public class TokenController {
    private static final Logger logger = LoggerFactory.getLogger(TokenController.class);

    @Autowired
    TokenService tokenService;
    @RequestMapping(path = "/getToken", method = {RequestMethod.GET})
    public Token getToken(@RequestParam("token") String token, HttpServletResponse response, HttpServletRequest request){
        response.setHeader("Access-Control-Allow-Origin",request.getHeader("Origin"));
        response.setHeader("Access-Control-Allow-Credentials","true");
        try {
            return tokenService.getToken(token);
        } catch (Exception e) {
            logger.error("getToken Exception:", e);
            return null;
        }
    }
}

package com.xiyou.demo.controller;


import com.xiyou.demo.model.Email;
import com.xiyou.demo.model.ResponseUser;
import com.xiyou.demo.model.User;
import com.xiyou.demo.service.EmailService;
import com.xiyou.demo.service.UserService;
import com.xiyou.demo.utils.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * 2 * @Author: wangyu
 * 3 * @Date: 2019/7/16 18:35
 * 4
 */
@RestController
@CrossOrigin
@RequestMapping(path = "/api")
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    @Autowired
    UserService userService;
    @Autowired
    EmailService emailService;
    @Autowired
    private JavaMailSender javaMailSender;

    /**
     * 根据邮箱获取验证码
     */
    @RequestMapping(path = {"/getCode"},method = {RequestMethod.GET})
    public Response getCode(String email, HttpServletResponse response, HttpServletRequest request){
        response.setHeader("Access-Control-Allow-Origin",request.getHeader("Origin"));
        response.setHeader("Access-Control-Allow-Credentials","true");
        Email email1=emailService.findByEmail(email);
        if (email1!=null){
            return  new Response(1, "此邮箱已注册");
        }
        try {
            userService.getCode(email);
        } catch (Exception e) {
            logger.error("获取验证码失败", e);
            return new Response(1, "获取验证码失败");
        }
        return  new Response(0, "已发送验证码到邮箱");
    }
    /**
     * 注册
     **/
    @RequestMapping(path = {"/reg"},method = {RequestMethod.POST})
    public Response userRegister(@RequestBody String jsonStr, HttpServletResponse response, HttpServletRequest request){
        response.setHeader("Access-Control-Allow-Origin",request.getHeader("Origin"));
        response.setHeader("Access-Control-Allow-Credentials","true");
        try {
            Map<String,String> map=userService.userRegister(jsonStr);
            if (map.get("msg").equals("注册成功")) {
                // msg为空代表 注册成功
                return new Response(0, map.get("msg"));
            } else {
                return new Response(1, map.get("msg"));
            }
        }catch (Exception e){
            logger.error("userRegister",e);
            return new Response(1,"注册失败");
        }
    }
    /**
     * 登录
     */
    @RequestMapping(path = {"/login"},method = {RequestMethod.GET})
    public Response userLogin(@RequestParam("username") String username, @RequestParam("password") String password, HttpServletResponse response, HttpServletRequest request){
        response.setHeader("Access-Control-Allow-Origin",request.getHeader("Origin"));
        response.setHeader("Access-Control-Allow-Credentials","true");
        try {
            Map<String, String> map = userService.userLogin(username, password);
            HttpSession session =request.getSession();
            String tokens = map.get("tokens");
            if (tokens == null) {
                return new Response(1, map.get("msg"));
            }
            Cookie cookie = new Cookie("token", tokens);
            cookie.setPath("/");
            cookie.setHttpOnly(true);
            response.addCookie(cookie);
            logger.info("登录成功");
            User user = userService.findUserByUsername(username);
            session.setAttribute("user",user);
            User user1 = (User) session.getAttribute("user");
            System.out.println(user);
            user.setStatus(1);
            userService.updateUser(user);
            return new Response(0, "登录成功", username,1);
        }catch (Exception e){
            logger.error("userLogin:" , e);
            return new Response(1, "登陆失败");
        }
    }
    /**
     * 根据token查找用户
     **/
    @RequestMapping(path = {"/getUserByToken"},method = {RequestMethod.GET})
    public ResponseUser getUserByTokens(@RequestParam("token") String token, HttpServletResponse response, HttpServletRequest request){
        response.setHeader("Access-Control-Allow-Origin",request.getHeader("Origin"));
        response.setHeader("Access-Control-Allow-Credentials","true");
        try {
            User user= userService.findUserByTokens(token);
            if(user==null){
                return  null;
            }
            ResponseUser responseUser=new ResponseUser();
            responseUser.setUid(user.getUid());
            responseUser.setUsername(user.getUsername());
            return responseUser;
        }catch (Exception e){
            logger.error("getUserByToken:" , e);
            return null;
        }

    }
    /**
     * 根据uid查找用户
     **/
    @RequestMapping(path = {"/getUserById"},method = {RequestMethod.GET})
    public ResponseUser getUserByUId(@RequestParam("uid") Integer uid, HttpServletResponse response, HttpServletRequest request){
        response.setHeader("Access-Control-Allow-Origin",request.getHeader("Origin"));
        response.setHeader("Access-Control-Allow-Credentials","true");
        try {
            User user=userService.findUserByUid(uid);
            if(user==null){
                return  null;
            }
            ResponseUser responseUser=new ResponseUser();
            responseUser.setUid(uid);
            responseUser.setUsername(user.getUsername());
            return responseUser;
        }catch (Exception e){
            logger.error("getUserById:" , e);
            return null;
        }

    }
    /**
     * 用户退出
     * @param
     * @return
     */
    @RequestMapping(path = "/logout", method = {RequestMethod.GET})
    @ResponseBody
    public Response userLogout(@RequestParam("username") String username,HttpServletResponse response, HttpServletRequest request){
        response.setHeader("Access-Control-Allow-Origin",request.getHeader("Origin"));
        response.setHeader("Access-Control-Allow-Credentials","true");
        try {
            User user = userService.findUserByUsername(username);
            user.setStatus(0);
            userService.updateUser(user);
            return new Response(0, "退出成功","object",0);
        } catch (Exception e) {
            logger.error("userLogout:" , e);
            return new Response(1, "退出失败");
        }
    }
    /**
     *获取tolen
     * @param
     * @return
     */
    @RequestMapping(path = "/getTokens", method = {RequestMethod.GET})
    @ResponseBody
    public void gettoken(@CookieValue("token") String token, HttpServletResponse response, HttpServletRequest request){
        response.setHeader("Access-Control-Allow-Origin",request.getHeader("Origin"));
        response.setHeader("Access-Control-Allow-Credentials","true");
    }
    /**
     * 根据账号获取丢失密码，发送到注册邮箱
     */
    @RequestMapping(path = "/findPassword",method = {RequestMethod.GET})
    @ResponseBody
    public Response findPassword(@RequestParam("username") String username,HttpServletResponse response, HttpServletRequest request){
        response.setHeader("Access-Control-Allow-Origin",request.getHeader("Origin"));
        response.setHeader("Access-Control-Allow-Credentials","true");
        User user=userService.findUserByUsername(username);
        Integer uid=user.getUid();
        Email email = emailService.findByUid(uid);
        SimpleMailMessage simpleMailMessage=new SimpleMailMessage();
        simpleMailMessage.setFrom("1271245636@qq.com");
        simpleMailMessage.setTo(email.getEmail());
        simpleMailMessage.setSubject("找回密码");
        simpleMailMessage.setText("您的密码是:" + user.getPassword());
        javaMailSender.send(simpleMailMessage);
        return new  Response(1,"密码已发送到您的注册邮箱");

    }
    /**
     * 用户修改个人资料(密码,vx,qq,phone)
     */
    @RequestMapping(value = "/updateUser",method = {RequestMethod.POST})
    @ResponseBody
    public Response updateUser(@RequestBody User user, HttpServletResponse response, HttpServletRequest request){
        response.setHeader("Access-Control-Allow-Origin",request.getHeader("Origin"));
        response.setHeader("Access-Control-Allow-Credentials","true");
        try {
            userService.updateUser(user);
            return new Response(1,"修改成功");
        }catch (Exception e){
            return new Response(0,"修改失败");
        }

    }
    /**
     * 根据账号获取个人信息
     */
    @RequestMapping(value = "/findUser",method = {RequestMethod.GET})
    @ResponseBody
    public User findUser(@RequestParam("username") String username, HttpServletResponse response, HttpServletRequest request){
        response.setHeader("Access-Control-Allow-Origin",request.getHeader("Origin"));
        response.setHeader("Access-Control-Allow-Credentials","true");
        try {
            return userService.findUserByUsername(username);
        }catch (Exception e){
            return null;
        }

    }
}
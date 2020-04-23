package com.xiyou.demo.controller;

import com.xiyou.demo.model.Goods;
import com.xiyou.demo.model.GoodsVO;
import com.xiyou.demo.model.User;
import com.xiyou.demo.service.GoodsService;
import com.xiyou.demo.service.UserService;
import com.xiyou.demo.utils.FileUtil;
import com.xiyou.demo.utils.Response;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.List;

/**
 * @Author: wangyu
 * @Date: 2020/1/28 14:25
 */
@RestController
@CrossOrigin
@RequestMapping(path = "/api")
public class GoodsController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    @Autowired
    UserService userService;
    @Autowired
    GoodsService goodsService;
    /**
     * 添加
     */

    @RequestMapping(value = "/addGoods",method ={RequestMethod.POST})
    @ResponseBody
    public String addGoods(@RequestBody Goods goods, HttpServletResponse response, HttpServletRequest request){
        response.setHeader("Access-Control-Allow-Origin",request.getHeader("Origin"));
        response.setHeader("Access-Control-Allow-Credentials","true");
        HttpSession session =request.getSession();
        User user= (User) session.getAttribute("user");
        Integer uid=user.getUid();
        goods.setUid(uid);
        try {
            goodsService.addGoods(goods);
        }catch (Exception e) {
            return "error";
        }


        return "success";
    }
    /**
     * 分类
     */
    @RequestMapping(value = "/getGoodsType",method ={RequestMethod.GET})
    @ResponseBody
    public List<String> getGoodsByType(HttpServletResponse response, HttpServletRequest request){
        response.setHeader("Access-Control-Allow-Origin",request.getHeader("Origin"));
        response.setHeader("Access-Control-Allow-Credentials","true");
        return goodsService.getGoodsType();
    }
    /**
     * 根据类型
     * @param type
     * @return
     */
    @RequestMapping(value = "/getGoodsByType",method ={RequestMethod.GET})
    @ResponseBody
    public List<GoodsVO> getGoodsByType(@RequestParam("type") String type,HttpServletResponse response, HttpServletRequest request){
        response.setHeader("Access-Control-Allow-Origin",request.getHeader("Origin"));
        response.setHeader("Access-Control-Allow-Credentials","true");
        return goodsService.getGoodsByType(type);
    }

    /**
     * 浏览全部 主页 未登录 分页
     */

    @RequestMapping(value = "/getGoods",method ={RequestMethod.GET})
    @ResponseBody
    public List<GoodsVO> getGoods(@RequestParam("page") Integer page, HttpServletResponse response, HttpServletRequest request){
        response.setHeader("Access-Control-Allow-Origin",request.getHeader("Origin"));
        response.setHeader("Access-Control-Allow-Credentials","true");
        System.out.println(page);
        return goodsService.getGoods(page);
    }
    /**
     * 信息的修改(根据gid),
     */
    @RequestMapping(value = "/updateGoods",method = {RequestMethod.POST})
    public String updateGoods(@RequestBody Goods goods, HttpServletResponse response, HttpServletRequest request){
        response.setHeader("Access-Control-Allow-Origin",request.getHeader("Origin"));
        response.setHeader("Access-Control-Allow-Credentials","true");
        HttpSession session =request.getSession();
        User user= (User) session.getAttribute("user");
        Integer uid=user.getUid();
        goods.setUid(uid);
        try {
            goodsService.updateGoodsById(goods);
        }catch (Exception e){
            return "error";
        }
        return "success";

    }
    /**
     * 查看自己发布的
     */
    @RequestMapping("/findMyGoods")
    @ResponseBody
    public List<Goods> findGoodsByUid(HttpServletResponse response, HttpServletRequest request){
        response.setHeader("Access-Control-Allow-Origin",request.getHeader("Origin"));
        response.setHeader("Access-Control-Allow-Credentials","true");
        try {
            HttpSession session =request.getSession();
            User user= (User) session.getAttribute("user");
            int uid=user.getUid();
            System.out.println(uid);
            return goodsService.findGoodsByUid(uid);
        }
        catch (Exception e){
            return null;
        }
    }
    /**
     * 根据gid删除商品
     */
    @RequestMapping("/deleteGoodsByGid")
    @ResponseBody
    public Response deleteGoodsByGid(@RequestParam("gid") int gid,HttpServletResponse response, HttpServletRequest request){
        response.setHeader("Access-Control-Allow-Origin",request.getHeader("Origin"));
        response.setHeader("Access-Control-Allow-Credentials","true");
        try {
            goodsService.deleteGoodsByGid(gid);

            return new Response(1,"删除成功");
        }
        catch (Exception e){
            return new Response(1,"error");
        }
    }
    /**
     * 根据gid查看商品详情
     */
    @RequestMapping("/getGoodsById")
    public Goods getGoodsById(@RequestParam("gid") Integer id,HttpServletResponse response, HttpServletRequest request){
        response.setHeader("Access-Control-Allow-Origin",request.getHeader("Origin"));
        response.setHeader("Access-Control-Allow-Credentials","true");
        return goodsService.getGoodsById(id);
    }
}

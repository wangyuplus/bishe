package com.xiyou.demo.controller;

import com.xiyou.demo.model.Goods;
import com.xiyou.demo.model.GoodsVO;
import com.xiyou.demo.model.User;
import com.xiyou.demo.service.GoodsService;
import com.xiyou.demo.service.UserService;
import com.xiyou.demo.utils.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: wangyu
 * @Date: 2020/1/28 14:25
 */
@Controller
@CrossOrigin
@ResponseBody
public class GoodsController {
    @Autowired
    UserService userService;
    @Autowired
    GoodsService goodsService;
    //添加
    @RequestMapping(value = "/addGoods",method ={RequestMethod.POST})
    public String addGoods(@RequestBody Goods goods, @CookieValue("token") String token){
        User user= userService.findUserByTokens(token);
        Integer uid=user.getUid();
        goods.setUid(uid);
        goodsService.addGoods(goods);
        return "添加成功";
    }

    @RequestMapping(value = "/getGoodsByType",method ={RequestMethod.GET})
    @ResponseBody
    public List<GoodsVO> getGoodsByType(@RequestParam("type") String type,@RequestParam("page") Integer page){

        return goodsService.getGoodsByType(type,page);
    }
    //浏览全部 主页 未登录
    @RequestMapping(value = "/getGoods",method ={RequestMethod.GET})
    @ResponseBody
    public List<GoodsVO> getGoods(@RequestParam("page") Integer page){
        return goodsService.getGoods(page);
    }
    /**
     * 信息的修改(根据gid),
     */
    @RequestMapping(value = "/updateGoods",method = {RequestMethod.POST})
    public Response updateGoods(Goods goods){
        try {
            goodsService.updateGoodsById(goods);
            return new Response(1,"修改成功");
        }catch (Exception e){
            return new Response(1,"error");
        }

    }
    /**
     * 查看自己发布的
     */
    @RequestMapping("/findGoodsByUid")
    @ResponseBody
    public List<Goods> findGoodsByUid( @CookieValue("token") String token){
        try {
            int uid=userService.findUserByTokens(token).getUid();
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
    public Response deleteGoodsByGid(@RequestParam("gid") int gid){
        try {
            goodsService.deleteGoodsByGid(gid);

            return new Response(1,"删除成功");
        }
        catch (Exception e){
            return new Response(1,"error");
        }
    }
}

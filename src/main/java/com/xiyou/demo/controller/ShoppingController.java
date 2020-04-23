package com.xiyou.demo.controller;

import com.xiyou.demo.model.Goods;
import com.xiyou.demo.model.Shopping;
import com.xiyou.demo.model.ShoppingVO;
import com.xiyou.demo.model.User;
import com.xiyou.demo.service.GoodsService;
import com.xiyou.demo.service.ShoppingService;
import com.xiyou.demo.service.UserService;
import com.xiyou.demo.utils.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ansi.AnsiOutput;
import org.springframework.boot.web.reactive.context.StandardReactiveWebEnvironment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: wangyu
 * @Date: 2020/1/30 16:31
 */
@RestController
@CrossOrigin
@RequestMapping(path = "/api")
public class ShoppingController {
    @Autowired
    ShoppingService shoppingService;
    @Autowired
    UserService userService;
    @Autowired
    GoodsService goodsService;

    /**
     * 添加到购物车
     */

    @RequestMapping(value = "/addShopping",method = RequestMethod.POST)
    @ResponseBody
    public Response addShopping(@RequestBody Shopping shopping, HttpServletResponse response, HttpServletRequest request){

        response.setHeader("Access-Control-Allow-Origin",request.getHeader("Origin"));
        response.setHeader("Access-Control-Allow-Credentials","true");
        HttpSession session =request.getSession();
        User user= (User) session.getAttribute("user");
        shopping.setUsername(user.getUsername());
        if(shoppingService.getGid(user.getUsername()).contains(shopping.getGid())){
            shoppingService.updateShopping(shopping.getGid(),user.getUsername());
            return new Response(1, "添加成功");
        }
        shoppingService.addShopping(shopping);
        return new Response(1, "添加成功");
    }
    /**
     * 查看自己的购物车
     */

    @RequestMapping("/getshop")
    public List<ShoppingVO> getshop(HttpServletResponse response, HttpServletRequest request,HttpSession session){
        response.setHeader("Access-Control-Allow-Origin",request.getHeader("Origin"));
        response.setHeader("Access-Control-Allow-Credentials","true");
        User user= (User) session.getAttribute("user");

        String username=user.getUsername();
        return  shoppingService.getshop(username);
    }

    /**
     * 删除购物车的物品 根据gid
     */

    @RequestMapping("/deleteShopById")
    public Response deleteGoods(@RequestParam("gid") Integer gid,HttpServletResponse response, HttpServletRequest request){
        response.setHeader("Access-Control-Allow-Origin",request.getHeader("Origin"));
        response.setHeader("Access-Control-Allow-Credentials","true");
        HttpSession session =request.getSession();
        User user= (User) session.getAttribute("user");
        String username=user.getUsername();
        shoppingService.deleteGid(gid,username);
        return new Response(1, "删除成功");
    }

    /**
     * 根据gid加
     */
    @RequestMapping("/addShopping")
    public String addShopping(@RequestParam("gid") int gid, HttpServletResponse response, HttpServletRequest request) {
        response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
        response.setHeader("Access-Control-Allow-Credentials", "true");
        HttpSession session =request.getSession();
        User user= (User) session.getAttribute("user");
        String username = user.getUsername();
        Goods goods=goodsService.getGoodsById(gid);
        int sum=shoppingService.getSumByGid(gid,username);
        if(sum+1>goods.getSum()){
            return "error";
        }
        shoppingService.updateShopping(gid,username);

        return "success";
    }
    /**
     * 根据gid减
     */
    @RequestMapping("/reduceShopping")
    public String reduceShopping(@RequestParam("gid") int gid, HttpServletResponse response, HttpServletRequest request) {
        response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
        response.setHeader("Access-Control-Allow-Credentials", "true");
        HttpSession session =request.getSession();
        User user= (User) session.getAttribute("user");
        String username = user.getUsername();
        shoppingService.reduceShopping(gid,username);
        return "success";
    }
    /**
     * 根据gid sum 更新数量
     */
    @RequestMapping("/updateShoppingSum")
    public String updateShopping(@RequestParam("gid") int gid,@RequestParam("sum") int sum, HttpServletResponse response, HttpServletRequest request) {
        response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
        response.setHeader("Access-Control-Allow-Credentials", "true");
        HttpSession session =request.getSession();
        User user= (User) session.getAttribute("user");
        String username = user.getUsername();
        shoppingService.updateShoppingSum(gid,sum,username);
        return "success";
    }
}

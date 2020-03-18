package com.xiyou.demo.controller;

import com.xiyou.demo.model.Shopping;
import com.xiyou.demo.model.ShoppingVO;
import com.xiyou.demo.model.User;
import com.xiyou.demo.service.GoodsService;
import com.xiyou.demo.service.ShoppingService;
import com.xiyou.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ansi.AnsiOutput;
import org.springframework.boot.web.reactive.context.StandardReactiveWebEnvironment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: wangyu
 * @Date: 2020/1/30 16:31
 */
@Controller
@CrossOrigin
@ResponseBody
public class ShoppingController {
    @Autowired
    ShoppingService shoppingService;
    @Autowired
    UserService userService;
    @Autowired
    GoodsService goodsService;

    //添加到购物车
    @RequestMapping("/addShopping")
    public String addShopping(@RequestBody Shopping shopping, @CookieValue("token") String token, HttpServletResponse response, HttpServletRequest request){
        response.setHeader("Access-Control-Allow-Origin",request.getHeader("Origin"));
        response.setHeader("Access-Control-Allow-Credentials","true");
        User user= userService.findUserByTokens(token);
        shopping.setUsername(user.getUsername());
        shoppingService.addShopping(shopping);
        return "成功";
    }
    //修改支付状态
    @RequestMapping("/pay")
    public String updateStatus(@RequestParam("username") String username,@CookieValue("token")String token, HttpServletResponse response, HttpServletRequest request){
        response.setHeader("Access-Control-Allow-Origin",request.getHeader("Origin"));
        response.setHeader("Access-Control-Allow-Credentials","true");
        User user= userService.findUserByTokens(token);
        String res="";
        if(user.getUsername().equals("wangyu")) {
            shoppingService.updateStatusByUsername(username);
            Map map = new HashMap();
            System.out.println(username);
            List<Integer> list=shoppingService.getGid(username);
            System.out.println(list.get(0));
            for(int i=0;i<list.size();i++){
                int sum = shoppingService.getSumByGid(Integer.valueOf(list.get(i)),username);
                goodsService.updateGoodsSum(Integer.valueOf(list.get(i)),sum);
            }

            res="支付成功";
            }

        else {
            res="无权限";
        }
        return res;
    }
    //查看自己的购物车
    @RequestMapping("/getshop")
    public List<ShoppingVO> getshop(@CookieValue("token")String token, HttpServletResponse response, HttpServletRequest request){
        response.setHeader("Access-Control-Allow-Origin",request.getHeader("Origin"));
        response.setHeader("Access-Control-Allow-Credentials","true");
        User user= userService.findUserByTokens(token);

        String username=user.getUsername();
        return  shoppingService.getshop(username);
    }
    //删除购物车的物品 根据gid
    @RequestMapping("/deleteShopById")
    public String deleteGoods(@CookieValue("token")String token,@RequestParam("gid") Integer gid,HttpServletResponse response, HttpServletRequest request){
        response.setHeader("Access-Control-Allow-Origin",request.getHeader("Origin"));
        response.setHeader("Access-Control-Allow-Credentials","true");
        User user= userService.findUserByTokens(token);
        String username=user.getUsername();
        shoppingService.deleteGid(gid,username);
        return  "删除成功";
    }

    /**
     * 更新购物车物品数量
     * 根据gid更新
     */
    @RequestMapping("/updateShopping")
    public String updateShopping(@CookieValue("token")String token,@RequestBody Shopping shopping, HttpServletResponse response, HttpServletRequest request) {
        response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
        response.setHeader("Access-Control-Allow-Credentials", "true");
        int sum =shopping.getSum();
        int gid=shopping.getGid();
        User user = userService.findUserByTokens(token);
        String username = user.getUsername();
        shoppingService.updateShopping(sum,gid,username);
        return "更新成功";
    }
}

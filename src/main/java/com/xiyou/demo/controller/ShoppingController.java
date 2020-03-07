package com.xiyou.demo.controller;

import com.xiyou.demo.model.Shopping;
import com.xiyou.demo.model.User;
import com.xiyou.demo.service.ShoppingService;
import com.xiyou.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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

    //添加到购物车
    @RequestMapping("/addShopping")
    public String addShopping(@RequestBody Shopping shopping, @CookieValue("token") String token){
        User user= userService.findUserByTokens(token);
        shopping.setUsername(user.getUsername());
        shoppingService.addShopping(shopping);
        return "成功";
    }
    //修改支付状态
    @RequestMapping("/pay")
    public String updateStatus(Shopping shopping){
        shoppingService.updateStatus(shopping.getGid());
        return "支付成功";
    }
}

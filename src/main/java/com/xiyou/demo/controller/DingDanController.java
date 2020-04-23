package com.xiyou.demo.controller;

import com.xiyou.demo.model.DingDan;
import com.xiyou.demo.model.User;
import com.xiyou.demo.service.DingDanService;
import com.xiyou.demo.service.GoodsService;
import com.xiyou.demo.service.ShoppingService;
import com.xiyou.demo.utils.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: wangyu
 * @Date: 2020/4/21 20:21
 */
@RestController
@CrossOrigin
@RequestMapping(path = "/api")
public class DingDanController {
    @Autowired
    DingDanService dingDanService;
    @Autowired
    ShoppingService shoppingService;
    @Autowired
    GoodsService goodsService;

    /**
     * 提交订单
     * @param money
     * @param response
     * @param request
     * @return
     */
    @RequestMapping(path = "/addDingDan")
    public String addDingDan (@RequestParam("money") int money, HttpServletResponse response, HttpServletRequest request){
        response.setHeader("Access-Control-Allow-Origin",request.getHeader("Origin"));
        response.setHeader("Access-Control-Allow-Credentials","true");
        HttpSession session =request.getSession();
        User user= (User) session.getAttribute("user");
        DingDan dingDan =new DingDan();
        dingDan.setMoney(money);
        dingDan.setUsername(user.getUsername());
        dingDanService.addDingDan(dingDan);
        return "success";
    }
    /**
     * 管理员wangyu同意
     */

    @RequestMapping("/agree")
    public String updateStatus(@RequestParam("username") String username, @RequestParam("did") int did,HttpServletResponse response, HttpServletRequest request){
        response.setHeader("Access-Control-Allow-Origin",request.getHeader("Origin"));
        response.setHeader("Access-Control-Allow-Credentials","true");
        HttpSession session =request.getSession();
        User user= (User) session.getAttribute("user");
        String response1=null;
        if(user.getUsername().equals("wangyu")) {
            dingDanService.updateStatusByID(did);
            System.out.println(username);
            List<Integer> list=shoppingService.getGid(username);
            for(int i=0;i<list.size();i++){
                int sum = shoppingService.getSumByGid(Integer.valueOf(list.get(i)),username);
                goodsService.updateGoodsSum(Integer.valueOf(list.get(i)),sum);
                shoppingService.deleteGid(Integer.valueOf(list.get(i)),username);
            }

            response1="success";
        }

        else {
            response1="无权限";
        }
        return response1;
    }
    /**
     * 管理员wangyu不同意
     */

    @RequestMapping("/noagree")
    public String noagree(@RequestParam("did") int did,HttpServletResponse response, HttpServletRequest request){
        response.setHeader("Access-Control-Allow-Origin",request.getHeader("Origin"));
        response.setHeader("Access-Control-Allow-Credentials","true");
        HttpSession session =request.getSession();
        User user= (User) session.getAttribute("user");
        String response1=null;
        if(user.getUsername().equals("wangyu")) {
            dingDanService.noagree(did);
            //dingDanService.deletedinngdan(did);

            response1="success";
        }

        else {
            response1="无权限";
        }
        return response1;
    }

    /**
     * 查看订单
     * @param response
     * @param request
     * @return
     */
    @RequestMapping(path = "/getDingdan")
    public List<DingDan> getDingdan (HttpServletResponse response, HttpServletRequest request){
        response.setHeader("Access-Control-Allow-Origin",request.getHeader("Origin"));
        response.setHeader("Access-Control-Allow-Credentials","true");
        return dingDanService.getDingDan();

    }
}

package com.xiyou.demo.model;

import lombok.Data;

/**
 * @Author: wangyu
 * @Date: 2020/3/8 17:10
 */
@Data
public class ShoppingVO {
    Integer gid;//物品id
    String name;//物品名字
    String type;//类型
    String price;//价格
    String path;//图片名字
    int shoppingsum;//购物车商品数量
    int goodssum;//仓库商品数量
    int status;//状态 0未支付 1已支付

}

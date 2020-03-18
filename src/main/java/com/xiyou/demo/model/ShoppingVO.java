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
    int sum;//物品数量
    int status;//状态 0未支付 1已支付

}

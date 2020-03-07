package com.xiyou.demo.model;

import lombok.Data;

/**
 * @Author: wangyu
 * @Date: 2020/1/30 16:13
 */
@Data
public class Shopping {
    Integer sid;
    String username;//账号
    Integer gid;//物品id
    int sum;//物品数量
    int pricesum;//总额
    int status;//状态 0未支付 1已支付
}

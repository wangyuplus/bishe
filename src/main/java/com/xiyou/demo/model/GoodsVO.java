package com.xiyou.demo.model;

import lombok.Data;

/**
 * @Author: wangyu
 * @Date: 2020/1/28 15:18
 */
@Data
public class GoodsVO {
    int gid;
    int uid;
    String name;//物品名称
    String type;//类型
    String price;//价格
    String path;//图片路径
    String beizhu;//备注
    int sum;//数量
    private String vx;
    private String qq;
    private String phone;
}

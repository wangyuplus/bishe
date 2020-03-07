package com.xiyou.demo.model;

import lombok.Data;

/**
 * @Author: wangyu
 * @Date: 2020/1/28 14:03
 */
@Data
public class Goods {
    Integer gid;
    Integer uid;
    String name;//物品名称
    String type;//类别
    String price;//价格
    int sum;//数量
    String path;//图片路径
    String beizhu;//备注

}

package com.xiyou.demo.model;

import lombok.Data;

/**
 * @Author: wangyu
 * @Date: 2020/4/21 20:23
 */
@Data
public class DingDan {
    int did;
    String username;
    int money;
    int status;//默认0 同意1 不同意2
}

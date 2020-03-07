package com.xiyou.demo.model;

import lombok.Data;

/**
 * 2 * @Author: wangyu
 * 3 * @Date: 2019/7/16 18:52
 * 4
 */
@Data
public class User {
    private Integer uid;
    private String username;
    private String password;
    private String salt;
    private Integer status;

    private String vx;
    private String qq;
    private String phone;

}

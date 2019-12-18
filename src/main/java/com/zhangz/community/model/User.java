package com.zhangz.community.model;

import lombok.Data;

/**
 * @author zhangz
 * @version 1.0
 * @date 2019/11/27 20:32
 */
@Data
public class User {
    private  Integer id;
    private  String name;
    private String accountId;
    private  String token;
    private  Long gmtCreate;
    private  Long gmtModified;
    private String bio;
    private  String avatarUrl;
}

package com.zhangz.community.dto;

import lombok.Data;

/**
 * @author zhangz
 * @version 1.0
 * @date 2019/11/25 22:46
 */
@Data
public class GitHubUser {
    private String name;
    private long id;
    private String bio;
    private  String avatar_url;


}

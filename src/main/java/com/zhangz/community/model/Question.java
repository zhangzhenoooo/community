package com.zhangz.community.model;

import lombok.Data;

/**
 * @author zhangz
 * @version 1.0
 * @date 2019/12/7 21:11
 */
@Data
public class Question {
    private Integer id;
    private String title;
    private String description;
    private  Long gmtCreate;
    private  Long gmtModified;
    private  Integer creator;
    private  Integer commentCount;
    private Integer viewCount;
    private Integer likeCount;
    private String tag;


}

package com.zhangz.community.dto;

import com.zhangz.community.model.User;
import lombok.Data;

/**
 * @author zhangz
 * @version 1.0
 * @date 2019/12/18 22:58
 */
@Data
public class QuestionDTO {
    private Long id;
    private String title;
    private String description;
    private  Long gmtCreate;
    private  Long gmtModified;
    private  Long creator;
    private  Integer commentCount;
    private Integer viewCount;
    private Integer likeCount;
    private String tag;
    private User user;
}

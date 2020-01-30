package com.zhangz.community.dto;

import com.zhangz.community.model.User;
import lombok.Data;

/**
 * @author zhangz
 * @version 1.0
 * @date 2020/1/28 16:32
 */
@Data
public class CommentDTO {
    private Long id;
    private Long parentId;
    private Integer type;
    private Long commentator;
    private Long gmtCreate;
    private Long gmtModified;
    private Long likeCount;
    private Integer commentCount;
    private String content;
    private User user;

}

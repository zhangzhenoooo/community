package com.zhangz.community.dto;

import lombok.Data;

/**
 * @author zhangz
 * @version 1.0
 * @date 2020/1/28 16:32
 */
@Data
public class CommentDTO {
    private Long parentId;
    private String content;
    private Integer type;

}

package com.zhangz.community.dto;

import com.zhangz.community.model.User;
import lombok.Data;

/**
 * @author zhangz
 * @version 1.0
 * @date 2020/2/2 21:45
 */
@Data
public class NotificationDTO {
    private Long id;
    private Long gmtCreate;
    private Integer status; //  status == 0 ? read : unread
    private Long notifier;//发起消息的人id
    private String notifierName;//发起消息的人name
    private String outerTitle;//问题或者评论的名称/内容
    private Long outerId;//问题或评论的ide
    private String typeName;//类型名称
    private Integer type;//类型，判断是针对问题的还是评论的
}

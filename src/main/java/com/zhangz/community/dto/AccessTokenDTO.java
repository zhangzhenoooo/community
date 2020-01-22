package com.zhangz.community.dto;

import lombok.Data;

/**
 * @author zhangz
 * @version 1.0
 * @date 2019/11/25 21:50
 */
@Data
public class AccessTokenDTO {

    private String client_id;
    private String client_secret;
    private String code ;
    private String redirect_uri;
    private String state;

}

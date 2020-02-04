package com.zhangz.community.dto;

import lombok.Data;

import javax.xml.soap.SAAJResult;

/**
 * @author zhangz
 * @version 1.0
 * @date 2020/2/4 19:50
 */
@Data
public class QuestionQuaryDTO {
    private String search;
    private String tag;
    private String sort;
    private Integer page;
    private Integer size;
}

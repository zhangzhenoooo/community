package com.zhangz.community.dto;

import lombok.Data;

import java.util.List;

/**
 * @author zhangz
 * @version 1.0
 * @date 2020/2/2 13:51
 */
@Data
public class TagDTO {
    private String category;
    private List<String> tags;

}

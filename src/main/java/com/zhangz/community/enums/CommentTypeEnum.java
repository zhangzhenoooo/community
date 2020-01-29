package com.zhangz.community.enums;

/**
 * @author zhangz
 * @version 1.0
 * @date 2020/1/28 20:18
 */
public enum CommentTypeEnum {
    QUESTION(1),
    COMMENT(2);

    private Integer type;

    CommentTypeEnum(Integer type) {
        this.type = type;
    }

    public static boolean isExist(Integer type) {
        for (CommentTypeEnum commentTypeEnum : CommentTypeEnum.values()){
            if (commentTypeEnum.getType() == type){
                return  true;
            }
        }
        return  false;
    }

    public Integer getType() {
        return type;
    }
}


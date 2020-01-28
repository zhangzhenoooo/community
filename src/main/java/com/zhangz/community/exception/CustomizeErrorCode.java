package com.zhangz.community.exception;

/**
 * @author zhangz
 * @version 1.0
 * @date 2020/1/28 15:16
 */
public enum CustomizeErrorCode implements ICustomizeErrorCode {

//枚举类型
    QUESTION_NOT_FIND("此问题不存在，请换个问题试试！");

    private  String message;

    CustomizeErrorCode(String message) {
        this.message = message;
    }
    @Override
    public String getMessage() {
        return message;
    }
}

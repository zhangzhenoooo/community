package com.zhangz.community.exception;

/**
 * @author zhangz
 * @version 1.0
 * @date 2020/1/28 15:16
 */
public enum CustomizeErrorCode implements ICustomizeErrorCode {

//枚举类型
    QUESTION_NOT_FIND(2001,"此问题不存在，请换个问题试试！"),
    TARGET_PARAM_NOT_FOUND(2002,"未选中任何问题或者评论进行回复"),
    NOT_LOGIN(2003,"当前操作需要先登录，请先登录再重试！"),
    SYSTEM_ERROR(2004,"服务器冒烟了，请稍后重试"),
    COMMENT_TYPE_WRONG(2005,"评论类型错误或者不存在"),
    COMMENT_NOT_FOUND(2006,"此评论不存在，请换个问题试试！"),
    COMMENT_INSERT_FAILED(2007,"评论失败"),
    FAVORITE_NOT_FOUND(2008,"还没有收藏哦，可能是系统偷懒了，熟悉一下吧！"),
    ;

    private Integer code;
    private  String message;


    CustomizeErrorCode(Integer code, String message) {
        this.message = message;
        this.code = code;
    }



    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public Integer getCode() {
        return code;
    }

}

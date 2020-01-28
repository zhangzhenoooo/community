package com.zhangz.community.exception;

/**
 * @author zhangz
 * @version 1.0
 * @date 2020/1/28 15:02
 */
public class CustomizeException extends RuntimeException {
    private String message;

    public CustomizeException(ICustomizeErrorCode errorCode) {
        this.message = errorCode.getMessage();
    }

    @Override
    public String getMessage() {
        return message;
    }
}

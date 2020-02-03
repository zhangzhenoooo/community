package com.zhangz.community.enums;

/**
 * @author zhangz
 * @version 1.0
 * @date 2020/2/2 21:01
 */
public enum NotificationStatusEnum {
    UNREAD(0),READ(1),
    ;

    private int status;

    public int getStatus() {
        return status;
    }

    NotificationStatusEnum(int status) {
        this.status = status;
    }
}

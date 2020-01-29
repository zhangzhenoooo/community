package com.zhangz.community.mapper;

import com.zhangz.community.model.Comment;

/**
 * @author zhangz
 * @version 1.0
 * @date 2020/1/28 22:05
 */
public interface CommentExtMapper {
    int incCommentCount(Comment comment);
}

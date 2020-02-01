package com.zhangz.community.controller;

import com.zhangz.community.dto.CommentDTO;
import com.zhangz.community.dto.ResultDTO;
import com.zhangz.community.enums.CommentTypeEnum;
import com.zhangz.community.exception.CustomizeErrorCode;
import com.zhangz.community.mapper.CommentMapper;
import com.zhangz.community.model.Comment;
import com.zhangz.community.model.User;
import com.zhangz.community.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhangz
 * @version 1.0
 * @date 2020/1/28 16:22
 */
@Controller
public class CommentController {

    @Autowired
    private CommentService commentService;

    @ResponseBody
    @RequestMapping(value = "/comment",method = RequestMethod.POST)
    public Object post(@RequestBody CommentDTO commentDTO,
                       HttpServletRequest request){

        User  user = (User) request.getSession().getAttribute("user");
        if (user == null){
            return ResultDTO.errorOf(CustomizeErrorCode.NOT_LOGIN);
        }
        if (commentDTO == null){
            return  ResultDTO.errorOf(CustomizeErrorCode.COMMENT_IS_NULL);
        }
        if (commentDTO.getContent() == null || "".equals(commentDTO.getContent())){
            return  ResultDTO.errorOf(CustomizeErrorCode.COMMENT_CONTENT_IS_NULL);
        }
        Comment comment = new Comment();
        comment.setParentId(commentDTO.getParentId());
        comment.setContent(commentDTO.getContent());
        comment.setType(commentDTO.getType());
        comment.setGmtModified(System.currentTimeMillis());
        comment.setGmtCreate(System.currentTimeMillis());
        comment.setCommentator(user.getId());
        comment.setCommentCount(0);
        comment.setLikeCount(0L);
        commentService.insert(comment);
        return  ResultDTO.succeed();
    }


//    二级评论
    @ResponseBody
    @RequestMapping(value = "/comment/{id}",method = RequestMethod.GET)
    public  ResultDTO  comments(@PathVariable (value = "id") Long id){

        List<CommentDTO> commentDTOS = commentService.listByTargetId(id, CommentTypeEnum.COMMENT);
        return  ResultDTO.succeed(commentDTOS);

    }
}


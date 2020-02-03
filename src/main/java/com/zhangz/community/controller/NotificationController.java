package com.zhangz.community.controller;

import com.zhangz.community.dto.NotificationDTO;
import com.zhangz.community.dto.PaginationDTo;
import com.zhangz.community.enums.NotificationTypeEnum;
import com.zhangz.community.model.User;
import com.zhangz.community.service.NotificationService;
import com.zhangz.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

/**
 * @author zhangz
 * @version 1.0
 * @date 2020/2/03 14:51
 */
@Controller
public class NotificationController {
    @Autowired
    private NotificationService notificationService;
    @Autowired
    private QuestionService questionService;

    @GetMapping("/notification/{id}")
    public  String profile(HttpServletRequest request,
                           @PathVariable(name = "id")Long id){

        User user = (User) request.getSession().getAttribute("user");
        if (user == null){
            return "redirect：/";
        }

        NotificationDTO notificationDTO = notificationService.read(id, user);
        if (NotificationTypeEnum.REPLY_QUESTION.getType() == notificationDTO.getType()
                ||NotificationTypeEnum.REPLY_COMMENT.getType() == notificationDTO.getType() ){
            return  "redirect:/question/"+notificationDTO.getOuterId();//跳转到相应的问题里面
        }else {
            return "redirect：/";
        }

    }
}

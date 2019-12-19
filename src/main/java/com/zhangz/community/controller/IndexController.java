package com.zhangz.community.controller;

import com.mysql.cj.x.protobuf.MysqlxDatatypes;
import com.zhangz.community.dto.QuestionDTO;
import com.zhangz.community.mapper.UserMapper;
import com.zhangz.community.model.User;
import com.zhangz.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author zhangz
 * @version 1.0
 * @date 2019/11/17 16:01
 */
@Controller
public class IndexController {
    //    @GetMapping("/")
//    public  String hello(@RequestParam(name = "name",required=false, defaultValue="") String name, Model model){
//        model.addAttribute("name",name);
//        return "index";
//    }
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private QuestionService questionService;

    @GetMapping("/")
    public String index(HttpServletRequest request,
                        Model model) {

        Cookie[] cookies = request.getCookies();
        if (cookies != null && cookies.length != 0) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("token")) {
                    String token = cookie.getValue();
                    User user = userMapper.findToken(token);
                    if (user != null) {
                        request.getSession().setAttribute("user", user);
                    }
                    break;
                }
            }
        }

        List<QuestionDTO> questionDTOList = questionService.list();
        model.addAttribute("questionList",questionDTOList);


        return "index";
    }
}

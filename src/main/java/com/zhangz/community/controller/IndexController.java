package com.zhangz.community.controller;

import com.zhangz.community.dto.PaginationDTo;
import com.zhangz.community.mapper.UserMapper;
import com.zhangz.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

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
                        Model model,
                        @RequestParam(name = "page",defaultValue = "1") Integer page,
                        @RequestParam(name = "size",defaultValue = "3") Integer size) {


        PaginationDTo pagination = questionService.list(page, size);
     //   System.out.println("test =  " + pagination.getTotalPage());
        model.addAttribute("pagination",pagination);
        return "index";
    }
}

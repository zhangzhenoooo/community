package com.zhangz.community.controller;

import com.zhangz.community.dto.PaginationDTo;
import com.zhangz.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author zhangz
 * @version 1.0
 * @date 2019/12/18 21:15
 */
@Controller
public class QuestionController {
    @Autowired
    private QuestionService questionService;

    @GetMapping("/questionList")
    public String questionList() {
        return "list_question";
    }

    @GetMapping("/question")
    public String question(
            Model model,
            @RequestParam(name = "page", defaultValue = "1") Integer page,
            @RequestParam(name = "size",defaultValue = "5") Integer size
    ){
        PaginationDTo paginationDTo = questionService.list(page, size);
        model.addAttribute("questionList",paginationDTo);
        return "list_question";
    }
}

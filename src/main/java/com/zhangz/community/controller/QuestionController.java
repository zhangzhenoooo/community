package com.zhangz.community.controller;

import com.zhangz.community.dto.QuestionDTO;
import com.zhangz.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
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
            Model model
    ){
        List<QuestionDTO> questionDTOList = questionService.list();
        model.addAttribute("questionList",questionDTOList);
        return "list_question";
    }
}

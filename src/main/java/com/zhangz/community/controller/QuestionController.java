package com.zhangz.community.controller;

import com.zhangz.community.dto.CommentDTO;
import com.zhangz.community.dto.PaginationDTo;
import com.zhangz.community.dto.QuestionDTO;
import com.zhangz.community.enums.CommentTypeEnum;
import com.zhangz.community.mapper.CommentMapper;
import com.zhangz.community.mapper.QuestionMapper;
import com.zhangz.community.service.CommentService;
import com.zhangz.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
    @Autowired
    private CommentService commentService;

    @GetMapping("/question/{id}")
    public String question(
            @PathVariable(name = "id")String id,
            Model model,
            @RequestParam(name = "page", defaultValue = "1") Integer page,
            @RequestParam(name = "size",defaultValue = "5") Integer size){
      Long    questionId = null;
      try {
          questionId =Long.parseLong(id);
      }catch (Exception e) {
          e.printStackTrace();
      }

        QuestionDTO questionDTO = questionService.getById(questionId);
        questionService.updateViewCount(questionId);
        List<CommentDTO> commentDTO = commentService.listByTargetId(questionId, CommentTypeEnum.QUESTION);

        List<QuestionDTO> relatedQuestions = questionService.selectRelates(questionDTO);
        model.addAttribute("question",questionDTO);
        model.addAttribute("comments",commentDTO);
        model.addAttribute("relatedQuestions",relatedQuestions);

       return "question" ;
    }
}

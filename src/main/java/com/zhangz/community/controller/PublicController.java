package com.zhangz.community.controller;

import com.zhangz.community.mapper.QuestionMapper;
import com.zhangz.community.mapper.UserMapper;
import com.zhangz.community.model.Question;
import com.zhangz.community.model.User;
import com.zhangz.community.service.QuestionService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author zhangz
 * @version 1.0
 * @date 2019/12/7 19:58
 */
@Controller
public class PublicController {
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private QuestionService questionService;

    @GetMapping("/publish")
    public String publish() {
        return "publish";
    }

    @PostMapping("/publish")
    public String doPublish(
            @RequestParam(value = "title",required = false,defaultValue = "") String title,
            @RequestParam(value = "description",required = false,defaultValue = "") String description,
            @RequestParam(value = "tag",required = false,defaultValue = "") String tag,
            @RequestParam(value = "id",required = false,defaultValue = "") Long id,
            HttpServletRequest request,
            Model model
    ) {

        Question question = new Question();
        question.setTitle(title);
        question.setTag(tag);
        question.setDescription(description);
        question.setId(id);

        model.addAttribute("question",question);

        //若用户未登录，跳转到首页
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            model.addAttribute("error", "用户未登录");
            return "publish";
        }

        if(title==null|| title.equals("")){
            model.addAttribute("error", "标题不能为空");
            return "publish";
        }
        if(description==null|| description.equals("")){
            model.addAttribute("error", "请输入问题描述");
            return "publish";
        }
        if(tag==null|| tag.equals("")){
            model.addAttribute("error", "请输标签");
            return "publish";
        }

        question.setCreator(user.getId());
        question.setGmtCreate(System.currentTimeMillis());
        question.setGmtModified(System.currentTimeMillis());

        questionService.insertOrUpdate(question);
//        questionMapper.create(question);
        return "redirect:/";
    }

    //跳转到修改问题界面
    @GetMapping("/publish/{id}")
    public String edit(
            Model model,
            @PathVariable(name = "id") Long id,
            HttpServletRequest request,
            HttpServletResponse response
    ){
        Question question = questionMapper.selectByPrimaryKey(id);
        model.addAttribute("question",question);
        return "publish";
    }
}

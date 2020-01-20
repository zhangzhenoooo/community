package com.zhangz.community.service;

import com.zhangz.community.dto.PaginationDTo;
import com.zhangz.community.dto.QuestionDTO;
import com.zhangz.community.mapper.QuestionMapper;
import com.zhangz.community.mapper.UserMapper;
import com.zhangz.community.model.Question;
import com.zhangz.community.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhangz
 * @version 1.0
 * @date 2019/12/18 23:06
 */
@Service
public class QuestionService {
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private UserMapper userMapper;


    public PaginationDTo list(Integer page, Integer size) {
        //size*(page - 1)
        PaginationDTo paginationDTo = new PaginationDTo();
        Integer totalCount = questionMapper.count();

        paginationDTo.setPagination(totalCount,page,size);

        if (page <1){
            page = 1;
        }
        if(page > paginationDTo.getTotalPage()){
            page = paginationDTo.getTotalPage();
        }

        Integer offset = size * (page - 1);
        List<Question> questionList = questionMapper.list(offset,size);
        List<QuestionDTO> questionDTOList = new ArrayList<>();

        for(Question question : questionList){
         User user= userMapper.findById(question.getCreator());
         QuestionDTO questionDTO  = new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }

        paginationDTo.setQuestionDTOList(questionDTOList);
        return  paginationDTo;

    }
}

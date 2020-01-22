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

        Integer totalPage;
        if (totalCount % size == 0) {
            totalPage = totalCount / size;
        } else {
            totalPage = totalCount / size + 1;
        }

        if (page < 1) {
            page = 1;
        }
        if (page > totalPage) {
            page = totalPage;
        }
        paginationDTo.setPagination(totalPage,page);
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



    public PaginationDTo  list(Integer userId,Integer page, Integer size) {

        PaginationDTo paginationDTo = new PaginationDTo();
        Integer totalCount = questionMapper.countByUserId(userId);



        Integer totalPage;
        if (totalCount % size == 0) {
            totalPage = totalCount / size;
        } else {
            totalPage = totalCount / size + 1;
        }
        //若totalPage = 0,则会出现size为负数的情况
        if (totalPage == 0){
            totalPage = 1;
         }
        if (page < 1) {
            page = 1;
        }
        if (page > totalPage) {
            page = totalPage;
        }
        paginationDTo.setPagination(totalPage,page);

        Integer offset = size * (page - 1);
        List<Question> questionList = questionMapper.listByUserId(userId,offset,size);
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


    public QuestionDTO getById(Integer id) {
        Question question = questionMapper.getById(id);
        QuestionDTO questionDTO  = new QuestionDTO();
        BeanUtils.copyProperties(question,questionDTO);
        User user= userMapper.findById(question.getCreator());
        questionDTO.setUser(user);
        return questionDTO;
    }

    public void insertOrUpdate(Question question) {
        Question q =questionMapper.getById(question.getId());
        if (q == null ){
            //新增
            questionMapper.create(question);
        }else {
            //更新
            q.setGmtModified(question.getGmtModified());
            q.setDescription(question.getDescription());
            q.setTag(question.getTag());
            q.setTitle(question.getTitle());
            questionMapper.update(q);
        }

    }
}

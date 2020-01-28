package com.zhangz.community.service;

import com.zhangz.community.dto.PaginationDTo;
import com.zhangz.community.dto.QuestionDTO;
import com.zhangz.community.exception.CustomizeErrorCode;
import com.zhangz.community.exception.CustomizeException;
import com.zhangz.community.mapper.QuestionMapper;
import com.zhangz.community.mapper.UserMapper;
import com.zhangz.community.model.Question;
import com.zhangz.community.model.QuestionExample;
import com.zhangz.community.model.User;
import org.apache.ibatis.session.RowBounds;
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
        Integer totalCount = Math.toIntExact(questionMapper.countByExample(new QuestionExample()));
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
        List<Question> questionList = questionMapper.selectByExampleWithRowbounds(new QuestionExample(),new RowBounds(offset,size));
        List<QuestionDTO> questionDTOList = new ArrayList<>();

        for(Question question : questionList){
            User user= userMapper.selectByPrimaryKey(question.getCreator());
            QuestionDTO questionDTO  = new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }

        paginationDTo.setQuestionDTOList(questionDTOList);
        return  paginationDTo;

    }



    public PaginationDTo  list(Long userId,Integer page, Integer size) {

        PaginationDTo paginationDTo = new PaginationDTo();
        QuestionExample questionExample = new QuestionExample();
        questionExample.createCriteria()
                .andCreatorEqualTo(userId);
        Integer totalCount = (int)questionMapper.countByExample(questionExample);



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
        QuestionExample example = new QuestionExample();
        example.createCriteria()
                .andCreatorEqualTo(userId);
        List<Question> questionList = questionMapper.selectByExampleWithRowbounds(example,new RowBounds(offset,size));
        List<QuestionDTO> questionDTOList = new ArrayList<>();

        for(Question question : questionList){
            User user= userMapper.selectByPrimaryKey(question.getCreator());
            QuestionDTO questionDTO  = new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }

        paginationDTo.setQuestionDTOList(questionDTOList);
        return  paginationDTo;

    }


    public QuestionDTO getById(Long id) {
        Question question = questionMapper.selectByPrimaryKey(id);
        if (question == null){
            throw  new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FIND);
        }
        QuestionDTO questionDTO  = new QuestionDTO();
        BeanUtils.copyProperties(question,questionDTO);
        User user= userMapper.selectByPrimaryKey(question.getCreator());
        questionDTO.setUser(user);
        return questionDTO;
    }

    public void insertOrUpdate(Question question) {
        Question dbQuestoin =questionMapper.selectByPrimaryKey(question.getId());
        if (dbQuestoin == null ){
            //新增
            question.setGmtCreate(System.currentTimeMillis());
            question.setGmtModified(question.getGmtCreate());
            question.setViewCount(0);
            question.setLikeCount(0);
            question.setCommentCount(0);
            questionMapper.insert(question);
        }else {
            //更新
            Question updateQuestion = new Question();
            updateQuestion.setGmtModified(System.currentTimeMillis());
            updateQuestion.setDescription(question.getDescription());
            updateQuestion.setTag(question.getTag());
            updateQuestion.setTitle(question.getTitle());
            QuestionExample example = new QuestionExample();
            example.createCriteria()
                    .andIdEqualTo(question.getId());
           int updated =  questionMapper.updateByExampleSelective(updateQuestion,example);
           if (updated  < 1){
               throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FIND);
           }
        }

    }

    public void updateViewCount(Long id) {
        Question dbQuestion = questionMapper.selectByPrimaryKey(id);
        Question updateQuestion = new Question();
        updateQuestion.setViewCount(dbQuestion.getViewCount()+1);
        QuestionExample example = new QuestionExample();
        example.createCriteria()
                .andIdEqualTo(id);
        questionMapper.updateByExampleSelective(updateQuestion,example);
    }
}

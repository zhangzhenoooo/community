package com.zhangz.community.mapper;

import com.zhangz.community.model.Question;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author zhangz
 * @version 1.0
 * @date 2019/12/7 21:09
 */
@Mapper
public interface QuestionMapper {
    @Insert("INSERT INTO  question( `title`, `description`, `gmt_create`, `gmt_modified`, `creator`,  `tag`) VALUES ( #{title},#{description},#{gmtCreate},#{gmtModified},#{creator},#{tag});")
    public  void  create(Question question);

    @Select("select question.id,question.title,question.description,question.gmt_create,question.gmt_modified,question.creator,question.comment_count,\n" +
        "question.view_count,question.like_count,question.tag from question limit #{offset},#{size}")
     public List<Question> list(@Param(value = "offset") Integer offset,@Param(value = "size") Integer size);

    @Select("select count(1) from question;")
    public   Integer count();
}

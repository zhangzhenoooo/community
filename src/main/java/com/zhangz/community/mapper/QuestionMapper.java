package com.zhangz.community.mapper;

import com.zhangz.community.model.Question;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author zhangz
 * @version 1.0
 * @date 2019/12/7 21:09
 */
@Mapper
public interface QuestionMapper {
    @Insert("INSERT INTO  question( `title`, `description`, `gmt_create`, `gmt_modified`, `creator`,  `tag`) VALUES ( #{title},#{description},#{gmtCreate},#{gmtModified},#{creator},#{tag});")
    public  void  create(Question question);
}

package com.zhangz.community.mapper;

import com.zhangz.community.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author zhangz
 * @version 1.0
 * @date 2019/11/27 20:27
 */
@Mapper
public interface UserMapper {


    @Insert("INSERT INTO user (name,account_id,token,gmt_create,gmt_modified,bio,avatar_url) VALUES(#{name},#{accountId},#{token},#{gmtCreate},#{gmtModified},#{bio},#{avatarUrl})")
    void insert(User user);


    @Select("SELECT `user`.id,`user`.account_id,`user`.`name`,`user`.token,`user`.gmt_create,`user`.gmt_modified,`user`.bio,`user`.avatar_url  FROM `user` WHERE  token = #{token}")
    User findToken(@Param("token") String token); //当参数不是对象时，要用 @Param 来注释

    @Select("SELECT `user`.id,`user`.account_id,`user`.`name`,`user`.token,`user`.gmt_create,`user`.gmt_modified,`user`.bio,`user`.avatar_url  FROM `user` WHERE  id = #{id}")
    User findById(@Param("id") Integer id);
}

package com.zhangz.community.mapper;

import com.zhangz.community.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author zhangz
 * @version 1.0
 * @date 2019/11/27 20:27
 */
@Mapper
public interface UserMapper {
    @Insert("INSERT INTO user (name,account_id,token,gmt_create,gmt_modified,bio,avatar_url) VALUES(#{name},#{accountId},#{token},#{gmtCreate},#{gmtModified},#{bio},#{avatarUrl})")
    void  insert(User user);

}

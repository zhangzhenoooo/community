package com.zhangz.community.service;

import com.zhangz.community.mapper.UserMapper;
import com.zhangz.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author zhangz
 * @version 1.0
 * @date 2020/1/22 13:15
 */
@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    public void createOrUpdate(User user) {
      User dbUser =   userMapper.findByAccountId(user.getAccountId());
      if (dbUser ==null){
          //插入
          user.setGmtCreate(System.currentTimeMillis());
          user.setGmtModified(user.getGmtCreate());
          userMapper.insert(user);

      }else {
          //更新
          dbUser.setGmtModified(user.getGmtModified());
          dbUser.setAvatarUrl(user.getAvatarUrl());
          dbUser.setName(user.getName());
          dbUser.setToken(user.getToken());
          userMapper.update(user);
      }

    }
}

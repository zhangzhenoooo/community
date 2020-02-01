package com.zhangz.community.controller;

import com.zhangz.community.dto.ResultDTO;
import com.zhangz.community.exception.CustomizeErrorCode;
import com.zhangz.community.mapper.FavoriteMapper;
import com.zhangz.community.model.Favorite;
import com.zhangz.community.model.FavoriteExample;
import com.zhangz.community.model.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author zhangz
 * @version 1.0
 * @date 2020/1/30 18:28
 */
@Controller
public class FavoriteController {

    @Autowired
    private FavoriteMapper favoriteMapper;

    @ResponseBody
    @RequestMapping(value = "/favorite",method = RequestMethod.POST)
    private  Object favorite(@RequestBody Favorite favorite,
                                 HttpServletRequest request){

        User user = (User) request.getSession().getAttribute("user");
        if (user == null){
            return ResultDTO.errorOf(CustomizeErrorCode.NOT_LOGIN);
        }

        FavoriteExample exampleFavorite = new FavoriteExample();
        exampleFavorite.createCriteria()
                .andCreatorEqualTo(user.getId())
                .andQuestionIdEqualTo(favorite.getQuestionId());
        List<Favorite> dbFavorites = favoriteMapper.selectByExample(exampleFavorite);
        //如果不为空的话，则为取消收藏

        if (dbFavorites.size() <= 0 ){
            //收藏
            Favorite f = new Favorite();
            f.setCreatedate(System.currentTimeMillis());
            f.setCreator(user.getId());
            f.setQuestionId(favorite.getQuestionId());
            f.setType(favorite.getType());
            f.setDeleted(0L);
            favoriteMapper.insert(f);
        }else {
            //取消收藏
            exampleFavorite.createCriteria()
                    .andQuestionIdEqualTo(dbFavorites.get(0).getQuestionId())
                    .andCreatorEqualTo(dbFavorites.get(0).getCreatedate());
            favoriteMapper.deleteByExample(exampleFavorite);

        }
        return ResultDTO.succeed();
    }
}

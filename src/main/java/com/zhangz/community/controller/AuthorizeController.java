package com.zhangz.community.controller;

import com.zhangz.community.dto.AccessTokerDTO;
import com.zhangz.community.dto.GitHubUser;
import com.zhangz.community.mapper.UserMapper;
import com.zhangz.community.model.User;
import com.zhangz.community.provider.GitHubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

/**
 * @author zhangz
 * @version 1.0
 * @date 2019/11/25 21:29
 */
@Controller
public class AuthorizeController {

    @Autowired   //自动实例化
    private GitHubProvider gitHubProvider;

    @Value("${github.client.id}")
    private  String clientId;
    @Value("${github.client.secre}")
    private  String  clientSecret;
    @Value("${github.redirect.uri}")
    private  String redirectUri;
    @Autowired
    private UserMapper userMapper;


    @GetMapping("/callback")
    public  String callback(@RequestParam(name = "code")String code,
                            @RequestParam ( name = "state")String state,
                            HttpServletRequest request){
        AccessTokerDTO accessTokerDTO = new AccessTokerDTO();
        accessTokerDTO.setClient_id(clientId);
        accessTokerDTO.setClient_secret(clientSecret);
        accessTokerDTO.setCode(code);
        accessTokerDTO.setRedirect_uri(redirectUri);
        accessTokerDTO.setState(state);
        String accessToken = gitHubProvider.getAccessToken(accessTokerDTO);
        GitHubUser gitHubUser = gitHubProvider.getUser(accessToken);
        if (gitHubUser != null ) {
            //登录成功
            User user  = new User();
            user.setAccountId(String.valueOf(gitHubUser.getId()));
            user.setAvatarUrl(null);
            user.setBio(null);
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            user.setName(gitHubUser.getName());
            user.setToken(UUID.randomUUID().toString());
            userMapper.insert(user);
           // System.out.println("gitHubUser = "+ gitHubUser.getName()+gitHubUser.getId()+gitHubUser.getBio());
            request.getSession().setAttribute("user",gitHubUser);
            return "redirect:/";
        } else {
//            log.error("callback get github error,{}", githubUser);
            // 登录失败，重新登录
            return "redirect:/";
        }
    }
}

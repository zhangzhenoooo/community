package com.zhangz.community.controller;

import com.zhangz.community.dto.AccessTokerDTO;
import com.zhangz.community.dto.GitHubUser;
import com.zhangz.community.provider.GitHubProvider;
import org.springframework.beans.factory.annotation.Autowired;
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
    @GetMapping("/callback")
    public  String callback(@RequestParam(name = "code")String code,
                            @RequestParam ( name = "state")String state,
                            HttpServletRequest request){
        AccessTokerDTO accessTokerDTO = new AccessTokerDTO();
        accessTokerDTO.setClient_id("4dace63cd03686b4f799");
        accessTokerDTO.setClient_secret("c6cc44cbc482153af3d4eed4b3b4a9af8485aef1");
        accessTokerDTO.setCode(code);
        accessTokerDTO.setRedirect_uri("http://localhost:8888/callback");
        accessTokerDTO.setState(state);
        String accessToken = gitHubProvider.getAccessToken(accessTokerDTO);
        GitHubUser user = gitHubProvider.getUser(accessToken);
        if (user != null ) {
            //登录成功
            System.out.println("user = "+ user.getName()+user.getId()+user.getBio());
            request.getSession().setAttribute("user",user);
            return "redirect:/";
        } else {
//            log.error("callback get github error,{}", githubUser);
            // 登录失败，重新登录
            return "redirect:/";
        }
    }
}

package com.zhangz.community.controller;

import com.zhangz.community.dto.AccessTokerDTO;
import com.zhangz.community.provider.GitHubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
                            @RequestParam ( name = "state")String state){
        AccessTokerDTO accessTokerDTO = new AccessTokerDTO();
        accessTokerDTO.setClient_id("4dace63cd03686b4f799");
        accessTokerDTO.setClient_secret("c6cc44cbc482153af3d4eed4b3b4a9af8485aef1");
        accessTokerDTO.setCode(code);
        accessTokerDTO.setRedirect_uri("http://localhost:8888/callback");
        accessTokerDTO.setState(state);
        gitHubProvider.getAccessToker(accessTokerDTO);
        return  "index";
    }
}

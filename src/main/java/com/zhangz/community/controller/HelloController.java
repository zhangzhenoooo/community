package com.zhangz.community.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author zhangz
 * @version 1.0
 * @date 2019/11/17 16:01
 */
@Controller
public class HelloController {
    @GetMapping("/hello")
    public  String hello(@RequestParam(name = "name",required=false, defaultValue="") String name, Model model){
        model.addAttribute("name",name);
        return "index";
    }
}

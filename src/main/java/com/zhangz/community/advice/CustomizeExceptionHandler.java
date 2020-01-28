package com.zhangz.community.advice;

import com.alibaba.fastjson.JSON;
import com.zhangz.community.exception.CustomizeException;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author zhangz
 * @version 1.0
 * @date 2020/1/28 14:38
 */
@ControllerAdvice
public class CustomizeExceptionHandler{
    @ExceptionHandler(Exception.class)
    ModelAndView handle(Throwable e, Model model, HttpServletRequest request, HttpServletResponse response) {
        if (e instanceof CustomizeException){
            model.addAttribute("message",e.getMessage());

        }else {
            model.addAttribute("message","服务器冒烟了，请稍后点击");
        }
        return new ModelAndView("error");

    }

    private HttpStatus getStatus (HttpServletRequest request){
        Integer statusCode =(Integer)request.getAttribute("javax.servlet.error.status_code");
        if (statusCode == null){
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return HttpStatus.valueOf(statusCode);
    }

}

package com.zhangz.community.provider;

import com.alibaba.fastjson.JSON;
import com.zhangz.community.dto.AccessTokerDTO;
import com.zhangz.community.dto.GitHubUser;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author zhangz
 * @version 1.0
 * @date 2019/11/25 21:46
 */
@Component
public class GitHubProvider {
    public  String getAccessToken(AccessTokerDTO accessTokerDTO){
   MediaType mediaType
            = MediaType.get("application/json; charset=utf-8");

    OkHttpClient client = new OkHttpClient();


        RequestBody body = RequestBody.create(mediaType,JSON.toJSONString(accessTokerDTO));
        Request request = new Request.Builder()
                .url("https://github.com/login/oauth/access_token")
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            String string= response.body().string();
//            System.out.println(string);
            String token = string.split("&")[0].split("=")[1];
            System.out.println(token);
            return token;
        } catch (Exception e) {
            e.printStackTrace();
        }
    return  null;
  }

  public GitHubUser getUser(String accessToken){
        OkHttpClient client = new OkHttpClient();
      Request request = new Request.Builder()
              .url("https://api.github.com/user?access_token="+accessToken)
              .build();
      try {
          Response response = client.newCall(request).execute();
          String string = response.body().string();
      GitHubUser gitHubUser = JSON.parseObject(string,GitHubUser.class);//自动将string类型的数据转化成json
          return  gitHubUser;
      } catch (IOException e) {
          e.printStackTrace();
      }
      return  null;


  }
}
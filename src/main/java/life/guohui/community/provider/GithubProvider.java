package life.guohui.community.provider;

import com.alibaba.fastjson.JSON;
import life.guohui.community.dto.AccessTokenDTO;
import life.guohui.community.dto.GithubUser;
import okhttp3.*;
import org.springframework.stereotype.Component;


import java.io.IOException;

@Component
public class GithubProvider {
    public String getAccessToken(AccessTokenDTO accessTokendDTO) {
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");

        OkHttpClient client = new OkHttpClient();


        RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(accessTokendDTO));
        Request request = new Request.Builder()
                .url("https://github.com/login/oauth/access_token?client_id="+accessTokendDTO.getCliend_id()+"&client_secret="+accessTokendDTO.getClient_secret()+"&code="+accessTokendDTO.getCode()+"&redirect_uri=http://localhost:8887/callback&state=1")
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            String string = response.body().string();
            //string:access_token=d33b7a020fb14111fb780e041834efdf890872f9&scope=&token_type=bearer
            //按照这个格式拿到token
            System.out.println(string);
            String token = string.split("&")[0].split("=")[1];
            return token;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public GithubUser getUser(String accessToken) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://api.github.com/user?access_token="+accessToken)
                .build();
        try{
            Response response = client.newCall(request).execute();
            String string = response.body().string();
            GithubUser githubUser = JSON.parseObject(string, GithubUser.class);
            return githubUser;
        }catch (IOException e){

        }
        return null;
    }
}

package life.guohui.community.controller;

import life.guohui.community.dto.AccessTokendDTO;
import life.guohui.community.dto.GithubUser;
import life.guohui.community.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthorizeController {
    
    @Autowired
    private GithubProvider githubProvider;

    @Value("${github.client.id}")
    private String clientId;

    @Value("${github.client.secret}")
    private String clientSecret;

    @Value("${github.redirect.uri}")
    private String redirectUri;
    
    @GetMapping("/callback")
    public String callback(@RequestParam(name="code") String code,
                           @RequestParam(name="state") String state){
        AccessTokendDTO accessTokendDTO = new AccessTokendDTO();
        accessTokendDTO.setCode(code);
        accessTokendDTO.setRedirect_uri(redirectUri);
        accessTokendDTO.setCliend_id(clientId);
        accessTokendDTO.setClient_secret("clientSecret");
        accessTokendDTO.setState(state);
        String accessToken = githubProvider.getAccessToken(accessTokendDTO);
        GithubUser user = githubProvider.getUser(accessToken);
        System.out.println(user.getName());
        return "index";
    }
}

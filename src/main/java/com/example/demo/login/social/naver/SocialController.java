package com.example.demo.login.social.naver;

import com.example.demo.login.social.naver.service.NaverService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@RequiredArgsConstructor
@Controller

public class SocialController {

    //private final 구글
    //private final KakaoService kakaoService;
    private final NaverService naverService;

    @RequestMapping(value="/", method= RequestMethod.GET)
    public String login(Model model) {
        //model.addAttribute("appleUrl", appleService.getAppleLogin());
        //model.addAttribute("kakaoUrl", kakaoService.getKakaoLogin());
        model.addAttribute("naverUrl", naverService.getNaverLogin());

        return "index";
    }
}
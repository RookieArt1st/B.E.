package com.example.demo.login.social.dto;

import java.io.Serializable;
import lombok.Getter;

@Getter
public class SessionUser implements Serializable {
    private String name;
    private String email;
    private String picture;

    public SessionUser(SocialDto googleDto){
        this.name = googleDto.getName();
        this.email = googleDto.getEmail();
        this.picture = googleDto.getPicture();
    }

}

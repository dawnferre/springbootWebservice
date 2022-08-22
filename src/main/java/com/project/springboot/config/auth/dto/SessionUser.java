package com.project.springboot.config.auth.dto;

import com.project.springboot.domain.user.User;
import lombok.Getter;

import java.io.Serializable;

@Getter
public class SessionUser implements Serializable {
    //인증된 사용자 정보
    private String name;
    private String email;
    private String picture;

    public SessionUser(User user){ //인증된 사용자 정보만 필요함. 
        this.name = user.getName();
        this.email = user.getEmail();
        this.picture = user.getPicture();
    }

}

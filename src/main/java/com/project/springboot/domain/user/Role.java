package com.project.springboot.domain.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {
    USER("ROLE_USER", "일반사용자"), //  한줄 선언. 각각  vo 선언으로 설정되면 오류남.
    GUEST("ROLE_GUEST","손님");


    private final String key;
    private final String title;



}

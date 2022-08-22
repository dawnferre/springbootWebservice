package com.project.springboot.config.auth;

import com.project.springboot.domain.user.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

        private final CustomeOAuth2UserService customOAuth2UserService;

        protected void configure(HttpSecurity http) throws  Exception{
            http.csrf().disable().headers().frameOptions().disable() // h2화면 사용하기 위해 옵션 disabled
            .and()
            .authorizeRequests()//URL별 권한 관리를 설정하는 옵션의 시작점
            .antMatchers("/api/v1/**")
             .hasRole(Role.USER.name())
            .anyRequest().authenticated() //인증된 사용자에게만 허용할 수 있게 설정 처리
            .and()
            .logout().logoutSuccessUrl("/") //로그아웃 기능에 대한 여러 설정의 진입점 ( 로그아웃 성공시 / 로 이동)
            .and()
            .oauth2Login().userInfoEndpoint().userService(customOAuth2UserService);
        }
}

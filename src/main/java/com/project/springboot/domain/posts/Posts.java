package com.project.springboot.domain.posts;

import com.project.springboot.domain.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter //getter 메소드 자동생성
@NoArgsConstructor //기본생성자 자동추가
@Entity //테이블과 링크될 클래스 , 기본값으로 클래스 카멜케이스 = 테이블 명 매칭 ( StringbootMatching = stringboot_matching)
public class Posts extends BaseTimeEntity {
    @Id //Pk필드 표시
    @GeneratedValue(strategy = GenerationType.IDENTITY) //pk의 생성 규칙, 뒤에 해당하는 옵션은 auto_increment
    private Long id;

    @Column(length = 500, nullable = false) //문자열의 경우 기본은 VARCHAR(255)
    private String title;

    @Column(columnDefinition = "TEXT",nullable = false)
    private String content;

    private String author;

    @Builder // 빌더 패턴 클래스를 생성, 생성자 산단에 선언시 생성자 포함된 필드만 빌더에 포함.
    public Posts(String title, String content, String author){
        this.title=title;
        this.content= content;
        this.author= author;

    }

    public void update(String title, String content){
        this.title = title;
        this.content= content;

    }
}

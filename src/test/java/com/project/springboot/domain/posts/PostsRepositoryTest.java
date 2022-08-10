package com.project.springboot.domain.posts;

import org.aspectj.lang.annotation.After;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

@ExtendWith(SpringExtension.class)
@TestPropertySource(locations = "classpath:/application.properties")
@SpringBootTest
public class PostsRepositoryTest {
    @Autowired
    PostsRepository postsRepository;

    @AfterEach //단위테스트 끝난후 실행될 메소드 지정, 테스트용 데이터베이스인 H2에 데이터가 남아있으면 그다음 테스트 실패할 확률이 있음
    public void cleanup(){
        postsRepository.deleteAll();
    }

    @Test
    public void loadingListData(){
        //given
        String title="테스트 게시글";
        String content ="테스트 본문";
        /**
         * insert/update 실행
         * - ID 값 존재하는 경우, update
         * - ID 값 존재 X , insert
         * */
        postsRepository.save(Posts.builder()
                .title(title)
                .content(content)
                .author("yeongjuan.ensnc@gmail.com")
                .build()
        );

        //when
        List<Posts> postsList = postsRepository.findAll();

        //then
        Posts posts = postsList.get(0);
        assertThat(posts.getTitle(),is(equalTo(title)));
        assertThat(posts.getContent(),is(equalTo(content)));
    }

    @Test
    public void insertBaseTimeEntity(){
        //given
        LocalDateTime now = LocalDateTime.of(2019,6,4,0,0,0);
        postsRepository.save(Posts.builder()
                .title("title")
                .content("content")
                .author("author")
                .build());
        //when
        List<Posts> postsList = postsRepository.findAll();

        //then
        Posts posts = postsList.get(0);

        System.out.println(">>>>>>>>>crateData="+posts.getCreatedDate()+", modifiedDate="+posts.getModifiedDate());

        assertThat(posts.getCreatedDate().isAfter(now),is(true));
        assertThat(posts.getModifiedDate().isAfter(now),is(true));


    }
}

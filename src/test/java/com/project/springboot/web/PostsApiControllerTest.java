package com.project.springboot.web;

import com.project.springboot.domain.posts.Posts;
import com.project.springboot.domain.posts.PostsRepository;
import com.project.springboot.web.dto.PostsSaveRequestDto;
import com.project.springboot.web.dto.PostsUpdateRequestDto;
import jdk.jfr.internal.Repository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import static org.hamcrest.Matchers.greaterThan;

import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PostsApiControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private PostsRepository postsRepository;

    @AfterEach
    public void tearDown() throws Exception{
        postsRepository.deleteAll();
    }

    @Test
    public void insertPosts() throws Exception{
        //given
        String title ="title";
        String content = "content";
        PostsSaveRequestDto reqiestDto = PostsSaveRequestDto.builder()
                .title(title)
                .content(content)
                .author("author")
                .build();

        String url ="http://localhost:"+port+"/api/v1/posts";

        //when
        ResponseEntity<Long> responseEntity = restTemplate.postForEntity(url,reqiestDto,Long.class);

        //then
        assertThat(responseEntity.getStatusCode(),is(equalTo(HttpStatus.OK)));
        assertThat(responseEntity.getBody(),allOf(greaterThan(0L))); // isgreaterThan 없어짐, 대안

        List<Posts> all = postsRepository.findAll();
        assertThat(all.get(0).getTitle(),is(equalTo(title)));
        assertThat(all.get(0).getContent(),is(equalTo(content)));

    }

    @Test
    public void updatePosts() throws Exception{
        //given
        Posts savedPosts = postsRepository.save(Posts.builder()
                .title("title")
                .content("content")
                .author("author")
                .build());
        Long updateId = savedPosts.getId();
        String expectedTitle ="title2";
        String expectedContent = "content2";

        PostsUpdateRequestDto requestDto = PostsUpdateRequestDto.builder()
                .title(expectedTitle)
                .content(expectedContent)
                .build();

        String url ="http://localhost:"+port+"/api/v1/posts/"+updateId;

        HttpEntity<PostsUpdateRequestDto> requestEntity = new HttpEntity<>(requestDto);

        //when
        ResponseEntity<Long> responseEntity = restTemplate.exchange(url, HttpMethod.PUT,requestEntity,Long.class);

        //then
        assertThat(responseEntity.getStatusCode(),is(equalTo(HttpStatus.OK)));
        assertThat(responseEntity.getBody(),allOf(greaterThan(0L))); // isgreaterThan 없어짐, 대안

        List<Posts> all = postsRepository.findAll();
        assertThat(all.get(0).getTitle(),is(equalTo(expectedTitle)));
        assertThat(all.get(0).getContent(),is(equalTo(expectedContent)));
    }
}

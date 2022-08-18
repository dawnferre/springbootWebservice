package com.project.springboot.service.posts;

import com.project.springboot.domain.posts.Posts;
import com.project.springboot.domain.posts.PostsRepository;
import com.project.springboot.web.dto.PostsListResponseDto;
import com.project.springboot.web.dto.PostsResponseDto;
import com.project.springboot.web.dto.PostsSaveRequestDto;
import com.project.springboot.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PostsService {
    private final PostsRepository postsRepository;

    public Long save(PostsSaveRequestDto reqiestDto){

        return postsRepository.save(reqiestDto.toEntity()).getId();
    }
    @Transactional
    public Long update(Long id, PostsUpdateRequestDto requestDto){
        Posts posts = postsRepository.findById(id)
                .orElseThrow(()-> new IllegalArgumentException("해당 게시글이 없습니다. id="+id));
        posts.update(requestDto.getTitle(), requestDto.getContent());

        return id;
    }

    public PostsResponseDto findById(Long id){
        Posts entity = postsRepository.findById(id)
                .orElseThrow(()-> new IllegalArgumentException("해당 게시글이 없습니다. id="+id));
        return new PostsResponseDto(entity);
    }
    @Transactional(readOnly =true) //트랙재션 범위는 유지하되, 조회 기능만 남겨두어 조회 속도가 개선 ( 조회기능만 있을경우, 추천)
    public List<PostsListResponseDto> findAllDesc(){
        return postsRepository.findAllDesc().stream()
                .map(PostsListResponseDto::new)//람다식 응용 . == .map(posts -> new PostsListResponseDto(posts)
                .collect(Collectors.toList());
    }
    @Transactional
    public void delete (Long id){
        Posts posts = postsRepository.findById(id)
                .orElseThrow(()-> new IllegalArgumentException("해당 게시글이 없습니다. id="+id));
        postsRepository.delete(posts);
    }
}

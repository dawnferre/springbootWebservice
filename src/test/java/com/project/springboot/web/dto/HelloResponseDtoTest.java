package com.project.springboot.web.dto;

import org.junit.jupiter.api.Test;
import static org.hamcrest.MatcherAssert.assertThat; // junit5 사용시
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.equalTo;

public class HelloResponseDtoTest {
    @Test
    public void lombokTest(){
        String name = "test";
        int amount = 1000;

        HelloResponseDto dto = new HelloResponseDto(name,amount);

        assertThat(dto.getName(),is(equalTo(name)));
        assertThat(dto.getAmount(),is(equalTo(amount)));
    }
}

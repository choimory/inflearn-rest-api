package com.choimory.inflearnrestapi.event;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import static org.assertj.core.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest
class EventControllerTest {
    @Autowired
    MockMvc mockMvc;

    /**
     * 이벤트 생성 테스트
     * @throws Exception - 컨트롤러에서 던지는 비즈니스 로직상의 예외
     */
    void createEvent() throws Exception{
        mockMvc.perform(post("")
                .contentType(MediaType.APPLICATION_PROBLEM_JSON_UTF8)
                .accept(MediaTypes.HAL_JSON))
                .andExpect(status().isCreated());
    }
}
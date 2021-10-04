package com.choimory.inflearnrestapi.events;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

//@RunWith(SpringRunner.class)
@ExtendWith(SpringExtension.class)
@WebMvcTest
class EventControllerTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;
    @MockBean //Mocking EventRepository
    EventRepository eventRepository;

    /**
     * 이벤트 생성 테스트
     * @throws Exception - 컨트롤러에서 던지는 비즈니스 로직상의 예외
     */
    @Test
    void createEvent() throws Exception {
        //Given
        Event param = Event.builder()
                .id(250L)
                .name("Spring")
                .description("Inflearn REST API study with Spring")
                .beginEnrollmentDateTime(LocalDateTime.of(2021,10,4,19,32))
                .closeEnrollmentDateTime(LocalDateTime.of(2021, 10, 4, 22, 0))
                .beginEventDateTime(LocalDateTime.of(2021, 10, 5, 12, 0))
                .basePrice(100)
                .maxPrice(200)
                .limitOfEnrollment(100)
                .location("강남역 D2 스타트업 팩토리")
                .build();

        //Stubbing EventRepository
        Mockito.when(eventRepository.save(param)).thenReturn(param);

        //When
        mockMvc.perform(post("/api/events")
                .contentType(MediaType.APPLICATION_PROBLEM_JSON_UTF8)
                .accept(MediaTypes.HAL_JSON)
                .content(objectMapper.writeValueAsString(param)))
                //Then
                .andExpect(status().isCreated())
                .andExpect(jsonPath("id").exists())
                .andExpect(header().exists(HttpHeaders.LOCATION)) //.andExpect(header().exists("Location"))
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaTypes.HAL_JSON_VALUE)) //.andExpect(header().string("Content-Type", "application/hal+json"))
                .andExpect(redirectedUrlPattern("http://{host}/api/events/{generated_id}"))
                .andDo(print());
    }
}
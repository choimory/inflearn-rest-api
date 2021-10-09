package com.choimory.inflearnrestapi.events;

import com.choimory.inflearnrestapi.config.ModelMapperConfigTest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
//WebMvcTest 슬라이싱 테스트에서 SpringBootTest 통합테스트로 전환
/*@WebMvcTest*/ @SpringBootTest @AutoConfigureMockMvc
//WebMvcTest 슬라이싱 테스트시 ModelMapper 빈 생성을 위해
//@Import({ModelMapperConfigTest.class})
class EventControllerTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;
    //WebMvcTest 슬라이싱 테스트 진행을 위한 EventRepository Mocking
    /*@MockBean
    EventRepository eventRepository;*/

    /**
     * 이벤트 생성 테스트
     * @throws Exception - 컨트롤러에서 던지는 비즈니스 로직상의 예외
     */
    @Test
    void createEvent() throws Exception {
        //Given
        Event param = Event.builder()
                .id(100L)
                .name("Spring")
                .description("Inflearn REST API study with Spring")
                .beginEnrollmentDateTime(LocalDateTime.of(2021,10,4,19,32))
                .closeEnrollmentDateTime(LocalDateTime.of(2021, 10, 4, 22, 0))
                .beginEventDateTime(LocalDateTime.of(2021, 10, 5, 12, 0))
                .basePrice(100)
                .maxPrice(200)
                .limitOfEnrollment(100)
                .location("강남역 D2 스타트업 팩토리")
                .free(true)
                .offline(false)
                .build();

        //Stubbing EventRepository
        //Mockito.when(eventRepository.save(param)).thenReturn(param);

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
                .andExpect(jsonPath("id").value(Matchers.not(100)))
                .andExpect(jsonPath("free").value(Matchers.not(true)))
                .andExpect(jsonPath("eventStatus").value(EventStatus.DRAFT.name()))
                .andDo(print());
    }
}
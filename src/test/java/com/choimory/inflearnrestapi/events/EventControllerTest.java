package com.choimory.inflearnrestapi.events;

import com.choimory.inflearnrestapi.TestDescription;
import com.choimory.inflearnrestapi.config.ModelMapperConfigTest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
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
/*@WebMvcTest*/ @SpringBootTest @AutoConfigureMockMvc //WebMvcTest 슬라이싱 테스트에서 SpringBootTest 통합테스트로 전환
//@Import({ModelMapperConfigTest.class}) //WebMvcTest 슬라이싱 테스트시 ModelMapper 빈 생성을 위해
class EventControllerTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;
    /*@MockBean
    EventRepository eventRepository;*/ //WebMvcTest 슬라이싱 테스트 진행을 위한 EventRepository Mocking

    /**
     * 이벤트 생성 테스트
     * @throws Exception - 컨트롤러에서 던지는 비즈니스 로직상의 예외
     */
    @Test
    @TestDescription("이벤트 생성 테스트")// 커스텀 어느테이션
    @DisplayName("이벤트 생성 테스트") // JUnit 5
    void createEvent() throws Exception {
        //-------------WebMvcTest를 이용한 슬라이싱 테스트를 진행할때의 레거시 로직------------
        
        //Given
        /*Event param = Event.builder()
                //.id(100L)
                .name("Spring")
                .description("Inflearn REST API study with Spring")
                .beginEnrollmentDateTime(LocalDateTime.of(2021,10,4,19,32))
                .closeEnrollmentDateTime(LocalDateTime.of(2021, 10, 4, 22, 0))
                .beginEventDateTime(LocalDateTime.of(2021, 10, 5, 12, 0))
                .basePrice(100)
                .maxPrice(200)
                .limitOfEnrollment(100)
                .location("강남역 D2 스타트업 팩토리")
                //.free(true)
                //.offline(false)
                .build();*/

        //WebMvcTest 슬라이싱 테스트 진행을 위한 EventRepository Stubbing
        //Mockito.when(eventRepository.save(param)).thenReturn(param);
        
        //-----------------SpringBootTest를 이용한 통합 테스트로 변경----------------------------

        EventRequestDto param = EventRequestDto.builder()
                //.id(100L)
                .name("Spring")
                .description("Inflearn REST API study with Spring")
                .beginEnrollmentDateTime(LocalDateTime.of(2021,10,4,19,32))
                .closeEnrollmentDateTime(LocalDateTime.of(2021, 10, 4, 22, 0))
                .beginEventDateTime(LocalDateTime.of(2021, 10, 5, 12, 0))
                .endEventDateTime(LocalDateTime.of(2021, 10, 6, 12, 0))
                .basePrice(100)
                .maxPrice(200)
                .limitOfEnrollment(100)
                .location("강남역 D2 스타트업 팩토리")
                //.free(true)
                //.offline(false)
                .build();

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

    /**
     * 이벤트 생성 요청 파라미터에 원하지 않는 요청 파라미터 건낼시 오류발생 확인
     * @throws Exception -
     */
    @Test
    @TestDescription("이벤트 생성 요청 파라미터에 원하지 않는 요청 파라미터 건낼시 오류발생 확인") // 커스텀 어느테이션
    @DisplayName("이벤트 생성 요청 파라미터에 원하지 않는 요청 파라미터 건낼시 오류발생 확인") // JUnit 5
    void createEvent_BadRequest_Wrong_Input() throws Exception {
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
                .eventStatus(EventStatus.PUBLISHED)
                .build();

        //When
        mockMvc.perform(post("/api/events")
                .contentType(MediaType.APPLICATION_PROBLEM_JSON_UTF8)
                .accept(MediaTypes.HAL_JSON)
                .content(objectMapper.writeValueAsString(param)))
                //Then
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    /**
     * 필수값 미포함 요청시 400에러 응답 테스트
     * @throws Exception -
     */
    @Test
    @TestDescription("필수값 미포함 요청시 400에러 응답 테스트")// 커스텀 어느테이션
    @DisplayName("필수값 미포함 요청시 400에러 응답 테스트") // JUnit 5
    void createEvent_BadRequest_Empty_Input() throws Exception{
        EventRequestDto param = EventRequestDto.builder().build();

        mockMvc.perform(post("/api/events")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(param)))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    /**
     * 종료일자가 시작일자보다 빠를때, 최대가격이 기본가격보다 낮을때 검증 테스트
     * @throws Exception -
     */
    @Test
    @TestDescription("종료일자가 시작일자보다 빠를때, 최대가격이 기본가격보다 낮을때 검증 테스트") // 커스텀 어느테이션
    @DisplayName("종료일자가 시작일자보다 빠를때, 최대가격이 기본가격보다 낮을때 검증 테스트") // JUnit 5
    void createEvent_BadRequest_Wrong_Input2() throws Exception{
        EventRequestDto param = EventRequestDto.builder()
                .name("Spring")
                .description("Inflearn REST API study with Spring")
                .beginEnrollmentDateTime(LocalDateTime.of(2021,10,5,19,32))
                .closeEnrollmentDateTime(LocalDateTime.of(2021, 10, 4, 22, 0))
                .beginEventDateTime(LocalDateTime.of(2021, 10, 6, 12, 0))
                .endEventDateTime(LocalDateTime.of(2021, 10, 5, 12, 0))
                .basePrice(10000)
                .maxPrice(200)
                .limitOfEnrollment(100)
                .location("강남역 D2 스타트업 팩토리")
                .build();

        mockMvc.perform(post("/api/events")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(param)))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }
}
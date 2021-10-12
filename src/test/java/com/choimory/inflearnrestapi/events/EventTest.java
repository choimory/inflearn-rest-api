package com.choimory.inflearnrestapi.events;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.*; // AssertJ 제공 Assertions
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class EventTest {
    /**
     * 빌더를 통한 인스턴스 생성이 가능한지를 확인한다
     */
    @Test
    public void checkBuilder(){
        String name = "Inflearn Spring REST Api", description = "REST API Dev With Spring";

        Event event = Event.builder()
                .name(name)
                .description(description)
                .build();

        //JUnit5
        org.junit.jupiter.api.Assertions.assertNotNull(event);
        org.junit.jupiter.api.Assertions.assertEquals(event.getName(), name);
        org.junit.jupiter.api.Assertions.assertEquals(event.getDescription(), description);

        //AssertJ
        assertThat(event).isNotNull();
        assertThat(event.getName()).isEqualTo(name);
        assertThat(event.getDescription()).isEqualTo(description);
    }

    /**
     * 자바 빈 스펙인 기본생성자, 게터, 세터를 이용한 인스턴스 생성이 가능한지 확인한다
     */
    @Test
    public void checkJavaBeanSpec(){
        Event event = new Event();
        String name = "event", desc = "spring";

        event.setName(name);
        event.setDescription(desc);

        assertThat(event.getName()).isEqualTo(name);
        assertThat(event.getDescription()).isEqualTo(desc);
    }

    @ParameterizedTest
    @MethodSource("paramsForTestFree")
    @DisplayName("free 응답값 확인")
    public void testFree(int basePrice, int maxPrice, boolean result){
        //given
        Event event = Event.builder()
                .basePrice(basePrice)
                .maxPrice(maxPrice)
                .build();

        //when
        event.update();

        //then
        Assertions.assertThat(event.isFree()).isEqualTo(result);
    }

    private static Stream<Arguments> paramsForTestFree(){
        //1번 방식
        return Stream.<Arguments>builder()
                .add(Arguments.arguments(0, 0, true))
                .add(Arguments.arguments(0, 100, false))
                .build();

        //2번 방식
        /*return Stream.of(
                Arguments.of(0, 0, true),
                Arguments.of(0, 100, false)
        );*/
    }


    @ParameterizedTest
    @MethodSource("paramsForTestOffline")
    @DisplayName("free 응답값 확인")
    public void testOffline(String location, boolean result){
        //given
        Event event = Event.builder()
                .location(location)
                .build();

        //when
        event.update();

        //then
        assertThat(event.isOffline()).isEqualTo(result);
    }

    private static Stream<Arguments> paramsForTestOffline(){
        return Stream.<Arguments>builder()
                .add(Arguments.arguments("강남역 네이버 D2 스타트업 팩토리", true))
                .add(Arguments.arguments(null, false))
                .build();
    }


    @ParameterizedTest
    @MethodSource("range")
    void testWithRangeMethodSource(int argument) {
        Assertions.assertThat(argument).isNotEqualTo(9);
    }

    static IntStream range() {
        return IntStream.range(0, 20).skip(10);
    }
}
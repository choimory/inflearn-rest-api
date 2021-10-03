package com.choimory.inflearnrestapi.event;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*; // JUnit 5 기본 Assertions
import static org.assertj.core.api.Assertions.*; // AssertJ 제공 Assertions

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
}
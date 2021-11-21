package com.choimory.inflearnrestapi.events;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.*;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.RepresentationModel;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Builder
@Getter
@RequiredArgsConstructor
public class EventResource extends RepresentationModel {
    @JsonUnwrapped //EventResource를 반환할때 event의 필드들을 event 객체 안에 감싸진 형태가 아닌 Event 필드들을 꺼내서 EventResource의 필드로 직렬화
    private Event event;

    public EventResource(Event event){
        this.event = event;
        add(linkTo(EventController.class).slash(event.getId()).withSelfRel()); //항상 포함되는 이벤트이므로 생성자에 포함
    }
}

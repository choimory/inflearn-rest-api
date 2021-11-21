package com.choimory.inflearnrestapi.events;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.server.core.LinkBuilderSupport;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.net.URI;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/api/events", produces = MediaTypes.HAL_JSON_VALUE)
public class EventController {
    private final EventRepository eventRepository;
    private final ModelMapper modelMapper;
    private final EventValidator eventValidator;

    @PostMapping
    public ResponseEntity createEvent(@RequestBody @Valid EventRequestDto param, Errors paramErrors){
        //Spring 기본 Validation 진행
        if(paramErrors.hasErrors()){
            return ResponseEntity.badRequest()
                    .body(paramErrors);
        }

        //Custom Validation 진행
        eventValidator.validate(param, paramErrors);
        if(paramErrors.hasErrors()){
            return ResponseEntity.badRequest()
                    .body(paramErrors);
        }

        Event entityParam = modelMapper.map(param, Event.class);
        entityParam.update();
        Event result = eventRepository.save(entityParam);

        //HATEOAS
        //chap3. 재사용하는 형식으로 변경
        /*URI uri = linkTo(EventController.class).slash(result.getId())
                .toUri();*/
        LinkBuilderSupport<WebMvcLinkBuilder> selfLinkBuilderSupport = linkTo(EventController.class).slash(result.getId());
        URI uri = selfLinkBuilderSupport.toUri();

        //chap3.링크 정보를 같이 제공해 줄수 있는 ResourceSupport 객체로 반환하는 코드로 변경
        /*return ResponseEntity.created(uri)
                .body(result);*/
        EventResource eventResource = new EventResource(result);
        //eventResource.add(selfLinkBuilderSupport.withSelfRel()); 항상 포함되어야 하는 내용이므로 생성자에서 호출시키도록 함
        eventResource.add(linkTo(EventController.class).withRel("query-events"));
        eventResource.add(selfLinkBuilderSupport.withRel("update-event"));

        return ResponseEntity.created(uri)
                .body(eventResource);
    }
}

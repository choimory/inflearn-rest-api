package com.choimory.inflearnrestapi.events;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.hateoas.MediaTypes;
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
        Event result = eventRepository.save(entityParam);

        //HATEOAS
        URI uri = linkTo(EventController.class).slash(result.getId())
                .toUri();

        return ResponseEntity.created(uri)
                .body(result);
    }
}

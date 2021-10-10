package com.choimory.inflearnrestapi.events;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
public class EventValidator {
    public void validate(EventRequestDto param, Errors errors){
        if((param.getBasePrice() > param.getMaxPrice()) && (param.getMaxPrice() > 0)){
            errors.rejectValue("basePrice", "wrongValue", "basePrice is wrong");
            errors.rejectValue("maxPrice", "wrongValue", "maxPrice is wrong");
        }

        LocalDateTime endEventDateTime = param.getEndEventDateTime();
        if(endEventDateTime.isBefore(param.getBeginEventDateTime())
                || endEventDateTime.isBefore(param.getCloseEnrollmentDateTime())
                || endEventDateTime.isBefore(param.getBeginEnrollmentDateTime())){
            errors.rejectValue("endEventDateTime", "wrongValue", "endEventDateTime is wrong");
        }

        //TODO beginEventDateTime Valid
        //TODO closeEnrollmentDateTime Valid
    }
}

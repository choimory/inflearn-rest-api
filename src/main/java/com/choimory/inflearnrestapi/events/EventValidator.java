package com.choimory.inflearnrestapi.events;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
public class EventValidator {
    public void validate(EventRequestDto param, Errors errors){
        if((param.getBasePrice() > param.getMaxPrice()) && (param.getMaxPrice() > 0)){
            errors.reject("wrongPrices", "prices are wrong"); //errors.reject() -> 글로벌 에러 (요청(객체) 전체에 대한 에러 정보)
        }

        LocalDateTime endEventDateTime = param.getEndEventDateTime();
        if(endEventDateTime.isBefore(param.getBeginEventDateTime())
                || endEventDateTime.isBefore(param.getCloseEnrollmentDateTime())
                || endEventDateTime.isBefore(param.getBeginEnrollmentDateTime())){
            errors.rejectValue("endEventDateTime", "wrongValue", "endEventDateTime is wrong"); //errors.rejectValue() -> 필드 에러 (해당 필드에 대한 에러 정보)
        }

        //TODO beginEventDateTime Valid
        //TODO closeEnrollmentDateTime Valid
    }
}

package com.choimory.inflearnrestapi.common;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.springframework.boot.jackson.JsonComponent;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

import java.io.IOException;

@JsonComponent //ObjectMapper에 등록하게 해주는 어노테이션
public class ErrorsSerializer extends JsonSerializer<Errors> {
    @Override
    public void serialize(Errors errors, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeStartArray();

        //errors.rejectValue()의 값들을 설정
        errors.getFieldErrors().forEach(e -> {
            try {
                gen.writeStartObject();

                gen.writeStringField("objectName", e.getObjectName());
                gen.writeStringField("field", e.getField());
                gen.writeStringField("code", e.getCode());
                gen.writeStringField("defaultMessage", e.getDefaultMessage());
                if(e.getRejectedValue() != null){
                    gen.writeStringField("rejectedValue", e.getRejectedValue().toString());
                }

                gen.writeEndObject();
            }catch (IOException e1){
                e1.printStackTrace();
            }
        });

        //errors.reject()의 값들을 설정
        errors.getGlobalErrors().forEach(e -> {
            try {
                gen.writeStartObject();

                gen.writeStringField("objectName", e.getObjectName());
                gen.writeStringField("code", e.getCode());
                gen.writeStringField("defaultMessage", e.getDefaultMessage());

                gen.writeEndObject();
            }catch (IOException e1){
                e1.printStackTrace();
            }
        });

        gen.writeEndArray();
    }
}

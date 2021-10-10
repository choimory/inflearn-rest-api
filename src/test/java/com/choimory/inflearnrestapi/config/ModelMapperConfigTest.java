package com.choimory.inflearnrestapi.config;

import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class ModelMapperConfigTest {
    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }
}
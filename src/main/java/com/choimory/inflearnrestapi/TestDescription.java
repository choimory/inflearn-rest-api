package com.choimory.inflearnrestapi;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD) //해당 어노테이션을 어디에 붙일것인가
@Retention(RetentionPolicy.SOURCE) //해당 어노테이션을 붙인 코드를 얼마나 오래 가져갈것인가? 1.클래스(기본) 2.소스(소스코드까지) 3.런타임(컴파일 후까지)
public @interface TestDescription {
    String value();
}

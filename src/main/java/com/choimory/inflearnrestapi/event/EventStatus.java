package com.choimory.inflearnrestapi.event;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum EventStatus {
    DRAFT,
    PUBLISHED,
    BEGAN_ENROLLMENT;
}

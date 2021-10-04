package com.choimory.inflearnrestapi.events;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum EventStatus {
    DRAFT,
    PUBLISHED,
    BEGAN_ENROLLMENT;
}

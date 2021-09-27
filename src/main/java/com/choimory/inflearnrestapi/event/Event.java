package com.choimory.inflearnrestapi.event;

import lombok.*;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Data
public class Event {
    private Long id;
    private String name;
    private String description;
    private LocalDateTime beginEnrollmentDateTime;
    private LocalDateTime closeEnrollmentDateTime;
    private LocalDateTime beginEventDateTime;
    private LocalDateTime endEventDateTime;
    private String location;
    private Integer basePrice;
    private Integer maxPrice;
    private Integer limitOfEnrollment;
    private boolean offline;
    private boolean free;
    private EventStatus eventStatus;
}

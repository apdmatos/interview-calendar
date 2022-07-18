package com.challenge.interview.calendar.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.OffsetDateTime;

@Data
@AllArgsConstructor
public class AvailabilityDto {
    private Long id;
    private OffsetDateTime from;
    private OffsetDateTime to;
}

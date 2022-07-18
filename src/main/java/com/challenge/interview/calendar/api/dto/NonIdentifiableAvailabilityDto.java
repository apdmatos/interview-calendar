package com.challenge.interview.calendar.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.OffsetDateTime;

@Data
@AllArgsConstructor
public class NonIdentifiableAvailabilityDto {
    private OffsetDateTime from;
    private OffsetDateTime to;
}

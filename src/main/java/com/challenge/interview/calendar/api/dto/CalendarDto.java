package com.challenge.interview.calendar.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class CalendarDto {
    private String id;
    private List<AvailabilityDto> availabilities;
}

package com.challenge.interview.calendar.api.controllers;

import com.challenge.interview.calendar.api.dto.NonIdentifiableAvailabilityDto;
import com.challenge.interview.calendar.model.TimeInterval;
import com.challenge.interview.calendar.services.AvailabilityService;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping({"/availabilities"})
public class AvailabilitiesController {
    private final AvailabilityService service;

    @Autowired
    public AvailabilitiesController(@NonNull AvailabilityService service) {
        this.service = service;
    }

    @GetMapping({"/{candidateId}"})
    @NonNull
    public List<NonIdentifiableAvailabilityDto> getAvailableSlots(@PathVariable @NonNull String candidateId,
                                                                  @RequestParam(name = "interviewers") @NonNull String interviewers) {
        String[] interviewerIds = interviewers.split(",");
        List<TimeInterval> timeIntervals = this.service.getAvailabilities(candidateId, Arrays.stream(interviewerIds).toList());

        return timeIntervals.stream()
                .map((timeInterval -> new NonIdentifiableAvailabilityDto(timeInterval.getFrom(), timeInterval.getTo())))
                .collect(Collectors.toList());
    }
}

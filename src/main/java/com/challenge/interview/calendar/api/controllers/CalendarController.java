package com.challenge.interview.calendar.api.controllers;

import com.challenge.interview.calendar.api.dto.AvailabilityDto;
import com.challenge.interview.calendar.api.dto.CalendarDto;
import com.challenge.interview.calendar.api.dto.NonIdentifiableAvailabilityDto;
import com.challenge.interview.calendar.api.validation.DateValidationRules;
import com.challenge.interview.calendar.model.AvailabilityEntity;
import com.challenge.interview.calendar.model.Role;
import com.challenge.interview.calendar.model.errors.HttpException;
import com.challenge.interview.calendar.services.CalendarAvailabilityService;
import java.util.List;
import java.util.stream.Collectors;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"/calendars"})
public class CalendarController {
    private final CalendarAvailabilityService service;

    @Autowired
    public CalendarController(@NonNull CalendarAvailabilityService service) {
        this.service = service;
    }

    @GetMapping({"/{role}/{id}/availabilities"})
    @NonNull
    public CalendarDto getCalendar(@PathVariable @NonNull Role role, @PathVariable @NonNull String id) {
        List<AvailabilityEntity> availabilities = this.service.findAllAvailabilities(id, role);
        List<AvailabilityDto> mappedAvailabilities = availabilities.stream()
                .map((availability) -> new AvailabilityDto(availability.getId(), availability.getFrom(), availability.getTo()))
                .collect(Collectors.toList());
        return new CalendarDto(id, mappedAvailabilities);
    }

    @PostMapping({"/{role}/{id}/availabilities"})
    @NonNull
    public AvailabilityDto createAvailability(@PathVariable @NonNull Role role,
                                              @PathVariable @NonNull String id,
                                              @RequestBody @NonNull NonIdentifiableAvailabilityDto availability) throws HttpException {

        DateValidationRules.validate(availability.getFrom(), availability.getTo());
        AvailabilityEntity res = this.service.addAvailability(id, role, availability.getFrom(), availability.getTo());
        return new AvailabilityDto(res.getId(), res.getFrom(), res.getTo());
    }

    @DeleteMapping({"/{role}/{id}/availabilities/{availabilityId}"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAvailability(@PathVariable @NonNull Role role, @PathVariable @NonNull String id, @PathVariable long availabilityId) {
        this.service.deleteAvailability(availabilityId, id, role);
    }
}

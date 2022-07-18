package com.challenge.interview.calendar.services;

import com.challenge.interview.calendar.model.AvailabilityEntity;
import com.challenge.interview.calendar.model.Role;
import com.challenge.interview.calendar.repository.AvailabilityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;

@Service
public class CalendarAvailabilityService {
    private final AvailabilityRepository repository;

    @Autowired
    public CalendarAvailabilityService(AvailabilityRepository repository) {
        this.repository = repository;
    }

    public AvailabilityEntity addAvailability(String calendarId, Role role, OffsetDateTime from, OffsetDateTime to) {
        OffsetDateTime toDateTime = to.withSecond(0).withNano(0);
        OffsetDateTime fromDateTime = from.withSecond(0).withNano(0);
        AvailabilityEntity entity = new AvailabilityEntity(fromDateTime, toDateTime, calendarId, role.toString());
        return this.repository.save(entity);
    }

    public List<AvailabilityEntity> findAllAvailabilities(String calendarId, Role role) {
        return this.repository.findByCalendarIdAndRole(calendarId, role.toString());
    }

    public void deleteAvailability(Long id, String calendarId, Role role) {
        this.repository.deleteByIdAndCalendarIdAndRole(id, calendarId, role.toString());
    }
}

package com.challenge.interview.calendar.repository;

import com.challenge.interview.calendar.model.AvailabilityEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AvailabilityRepository extends CrudRepository<AvailabilityEntity, Long> {
    List<AvailabilityEntity> findByCalendarIdAndRole(String calendarId, String role);
    void deleteByIdAndCalendarIdAndRole(Long id, String calendarId, String role);
}

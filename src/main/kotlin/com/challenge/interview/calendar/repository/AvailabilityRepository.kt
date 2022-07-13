package com.challenge.interview.calendar.repository

import com.challenge.interview.calendar.model.AvailabilityEntity
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface AvailabilityRepository: CrudRepository<AvailabilityEntity, Long> {
    fun findByCalendarIdAndRole(calendarId: String, role: String): List<AvailabilityEntity>;
    fun deleteByIdAndCalendarIdAndRole(id: Long, calendarId: String, role: String);
}
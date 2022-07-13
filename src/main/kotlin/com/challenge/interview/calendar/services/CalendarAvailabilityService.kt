package com.challenge.interview.calendar.services

import com.challenge.interview.calendar.model.AvailabilityEntity
import com.challenge.interview.calendar.model.Role
import com.challenge.interview.calendar.repository.AvailabilityRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.time.OffsetDateTime

@Service
class CalendarAvailabilityService
    @Autowired
    constructor (private val repository: AvailabilityRepository) {
    fun addAvailability(calendarId: String, role: Role, from: OffsetDateTime, to: OffsetDateTime): AvailabilityEntity {
        val toDateTime = to.withSecond(0).withNano(0)
        val fromDateTime = from.withSecond(0).withNano(0)
        val entity = AvailabilityEntity(fromDateTime, toDateTime, calendarId, role.toString());
        return this.repository.save(entity);
    }

    fun findAllAvailabilities(calendarId: String, role: Role): List<AvailabilityEntity> {
        return this.repository.findByCalendarIdAndRole(calendarId, role.toString());
    }

    fun deleteAvailability(id: Long, calendarId: String, role: Role) {
        this.repository.deleteByIdAndCalendarIdAndRole(id, calendarId, role.toString());
    }
}
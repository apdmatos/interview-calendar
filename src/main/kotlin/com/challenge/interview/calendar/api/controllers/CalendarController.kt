package com.challenge.interview.calendar.api.controllers

import com.challenge.interview.calendar.api.dto.AvailabilityDto
import com.challenge.interview.calendar.api.dto.CalendarDto
import com.challenge.interview.calendar.api.dto.NonIdentifiableAvailabilityDto
import com.challenge.interview.calendar.model.Role
import com.challenge.interview.calendar.api.validation.DateValidationRules
import com.challenge.interview.calendar.services.CalendarAvailabilityService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/calendars")
class CalendarController
    @Autowired
    constructor(private val service: CalendarAvailabilityService) {

    @GetMapping("/{role}/{id}/availabilities")
    fun getCalendar(@PathVariable role: Role,
                    @PathVariable id: String): CalendarDto {
        val availabilities = this.service.findAllAvailabilities(id, role);
        val mappedAvailabilities = availabilities.map { AvailabilityDto(it.id!!, it.from, it.to) }
        return CalendarDto(1, mappedAvailabilities);
    }

    @PostMapping("/{role}/{id}/availabilities")
    fun createAvailability(@PathVariable role: Role,
                           @PathVariable id: String,
                           @RequestBody availability: NonIdentifiableAvailabilityDto): AvailabilityDto {
        DateValidationRules.validate(availability.from, availability.to);
        val res = this.service.addAvailability(id, role, availability.from, availability.to)
        return AvailabilityDto(res.id!!, res.from, res.to);
    }

    @DeleteMapping("/{role}/{id}/availabilities/{availabilityId}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    fun deleteAvailability(@PathVariable role: Role,
                           @PathVariable id: String,
                           @PathVariable availabilityId: Long) {
        this.service.deleteAvailability(availabilityId, id, role);
    }
}
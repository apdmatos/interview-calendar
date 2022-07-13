package com.challenge.interview.calendar.api.controllers

import com.challenge.interview.calendar.api.dto.NonIdentifiableAvailabilityDto
import com.challenge.interview.calendar.services.AvailabilityService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/availabilities")
class AvailabilitiesController
    @Autowired
    constructor(private val service: AvailabilityService) {
    @GetMapping("/{candidateId}")
    fun getAvailableSlots(@PathVariable candidateId: String,
                          @RequestParam(name = "interviewers", required = true) interviewers: String): List<NonIdentifiableAvailabilityDto> {
        val interviewerIds = interviewers.split(",")
        val timeIntervals = service.getAvailabilities(candidateId, interviewerIds)

        return timeIntervals.map { NonIdentifiableAvailabilityDto(it.from, it.to) }
    }
}
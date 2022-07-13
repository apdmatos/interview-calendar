package com.challenge.interview.calendar.services

import com.challenge.interview.calendar.model.Role
import com.challenge.interview.calendar.model.TimeInterval
import com.challenge.interview.calendar.repository.AvailabilityRepository
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class AvailabilityService
    @Autowired
    constructor (private val repository: AvailabilityRepository) {

    private val log = LoggerFactory.getLogger(this.javaClass.name)

    fun getAvailabilities(candidateId: String, interviewerIds: List<String>): List<TimeInterval> {
        val candidateAvailabilities = repository.findByCalendarIdAndRole(candidateId, Role.CANDIDATE.toString());
        val interviewerAvailabilities = interviewerIds.map {
            repository.findByCalendarIdAndRole(it, Role.INTERVIEWER.toString())
        }

        //val candidateTimeIntervals = candidateAvailabilities.map { it.toTimeInterval() }
        var intersectedIntervals = candidateAvailabilities.map { it.toTimeInterval() }
        interviewerAvailabilities.forEach {
            val interviewerIntervals = it.map { it.toTimeInterval() }
            intersectedIntervals = intersect(intersectedIntervals, interviewerIntervals)
        }

        return intersectedIntervals
    }

    private fun intersect(intersectedIntervals: List<TimeInterval>, interviewerIntervals: List<TimeInterval>): List<TimeInterval> {
        val list = mutableListOf<TimeInterval>()
        interviewerIntervals.forEach{
            val interval = it
            interviewerIntervals.forEach{
                val overlapInterval = interval.overlap(it)
                if (overlapInterval != null)
                    list.add(overlapInterval)
            }
        }
        return list
    }
}
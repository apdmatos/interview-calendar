package com.challenge.interview.calendar.model

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import java.time.OffsetDateTime

internal class TimeIntervalTest {

    @Test
    fun overlap_should_return_the_overlap_time_interval_when_dates_overlap() {
        val date1 = TimeInterval(OffsetDateTime.now(), OffsetDateTime.now().plusHours(10))
        val date2 = TimeInterval(OffsetDateTime.now().plusHours(1), OffsetDateTime.now().plusHours(2))

        val overlapPeriod = date1.overlap(date2)
        assertNotNull(overlapPeriod)
        assertEquals(date2.from, overlapPeriod?.from)
        assertEquals(date2.to, overlapPeriod?.to)
    }

    @Test
    fun overlap_should_return_null_if_dates_do_not_overlap() {
        val date1 = TimeInterval(OffsetDateTime.now(), OffsetDateTime.now().plusHours(1))
        val date2 = TimeInterval(OffsetDateTime.now().plusHours(1), OffsetDateTime.now().plusHours(2))

        val overlapPeriod = date1.overlap(date2)
        assertNull(overlapPeriod)
    }
}
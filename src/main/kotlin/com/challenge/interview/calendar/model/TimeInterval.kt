package com.challenge.interview.calendar.model

import java.time.OffsetDateTime

class TimeInterval(val from: OffsetDateTime, val to: OffsetDateTime) {
    fun overlap(timeInterval: TimeInterval): TimeInterval? {
        if (timeInterval.from > this.to
            || this.from > timeInterval.to
            || this.to < this.from
            || timeInterval.to < timeInterval.from) {
            return null
        }
        val intersectedFrom = if (this.from < timeInterval.from) timeInterval.from else this.from
        val intersectedTo = if (this.to < timeInterval.to) this.to else timeInterval.to
        return TimeInterval(intersectedFrom, intersectedTo)
    }
}
package com.challenge.interview.calendar.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import java.time.OffsetDateTime

@Entity(name = "availability")
class AvailabilityEntity() {
    public constructor(from: OffsetDateTime,
                       to: OffsetDateTime,
                       calendarId: String,
                       role: String): this() {
        this.from = from
        this.to = to
        this.calendarId = calendarId;
        this.role = role;
    }

    @Column(name = "fromDate", nullable = false)
    lateinit var from: OffsetDateTime;

    @Column(name = "toDate", nullable = false)
    lateinit var to: OffsetDateTime;

    @Column(nullable = false)
    lateinit var calendarId: String;

    @Column(nullable = false)
    lateinit var role: String;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = null;

    fun toTimeInterval(): TimeInterval {
        return TimeInterval(this.from, this.to)
    }
}
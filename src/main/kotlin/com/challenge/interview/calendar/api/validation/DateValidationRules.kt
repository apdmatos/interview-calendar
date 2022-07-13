package com.challenge.interview.calendar.api.validation

import com.challenge.interview.calendar.model.errors.BadRequestException
import java.time.OffsetDateTime

object DateValidationRules {

    @Throws(BadRequestException::class)
    fun validate(from: OffsetDateTime, to: OffsetDateTime) {
        if (!isIntervalInFuture(from, to)){
            throw BadRequestException("interval must be in future");
        }

        if (!isFromBeforeThanTo(from, to)){
            throw BadRequestException("to must be after then from");
        }

        if (!isIntervalValid(from, to)) {
            throw BadRequestException("interval is invalid. It must start at 00 and finish at 00");
        }
    }

    fun isIntervalValid(from: OffsetDateTime, to: OffsetDateTime): Boolean {
        return from.minute == 0 && to.minute == 0;
    }

    fun isIntervalInFuture(from: OffsetDateTime, to: OffsetDateTime): Boolean {
        val now = OffsetDateTime.now();
        return from > now && to > now;
    }

    fun isFromBeforeThanTo(from: OffsetDateTime, to: OffsetDateTime): Boolean {
        return from < to;
    }
}
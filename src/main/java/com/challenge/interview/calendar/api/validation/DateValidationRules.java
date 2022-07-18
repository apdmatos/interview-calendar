package com.challenge.interview.calendar.api.validation;

import com.challenge.interview.calendar.model.errors.BadRequestException;

import java.time.OffsetDateTime;

public class DateValidationRules {

    public static void validate(OffsetDateTime from, OffsetDateTime to) throws BadRequestException {
        if (!isIntervalInFuture(from, to)){
            throw new BadRequestException("interval must be in future");
        }

        if (!isFromBeforeThanTo(from, to)){
            throw new BadRequestException("to must be after then from");
        }

        if (!isIntervalValid(from, to)) {
            throw new BadRequestException("interval is invalid. It must start at 00 and finish at 00");
        }
    }

    public static boolean isIntervalValid(OffsetDateTime from, OffsetDateTime to) {
        return from.getMinute() == 0 && to.getMinute() == 0;
    }

    public static boolean isIntervalInFuture(OffsetDateTime from, OffsetDateTime to) {
        OffsetDateTime now = OffsetDateTime.now();
        return from.isAfter(now) && to.isAfter(now);
    }

    public static boolean isFromBeforeThanTo(OffsetDateTime from, OffsetDateTime to) {
        return from.isBefore(to);
    }
}

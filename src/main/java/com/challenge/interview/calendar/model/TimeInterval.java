package com.challenge.interview.calendar.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.OffsetDateTime;

@Getter
@AllArgsConstructor
public class TimeInterval {

    private OffsetDateTime from;
    private OffsetDateTime to;

    public TimeInterval overlap(TimeInterval timeInterval) {
        if (timeInterval.getFrom().isAfter(this.to)
                || this.from.isAfter(timeInterval.getTo())
                || this.to.isBefore(this.from)
                || timeInterval.getTo().isBefore(timeInterval.getFrom())) {
            return null;
        }
        OffsetDateTime intersectedFrom = this.from.isBefore(timeInterval.getFrom()) ? timeInterval.from : this.from;
        OffsetDateTime intersectedTo = this.to.isBefore(timeInterval.to) ?  this.to : timeInterval.to;
        return new TimeInterval(intersectedFrom, intersectedTo);
    }
}

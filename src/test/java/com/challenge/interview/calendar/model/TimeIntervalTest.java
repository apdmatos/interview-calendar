package com.challenge.interview.calendar.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import java.time.OffsetDateTime;

public class TimeIntervalTest {
    @Test
    void overlap_should_return_the_overlap_time_interval_when_dates_overlap() {
        TimeInterval date1 = new TimeInterval(OffsetDateTime.now(), OffsetDateTime.now().plusHours(10));
        TimeInterval date2 = new TimeInterval(OffsetDateTime.now().plusHours(1), OffsetDateTime.now().plusHours(2));

        TimeInterval overlapPeriod = date1.overlap(date2);
        Assertions.assertNotNull(overlapPeriod);
        Assertions.assertEquals(date2.getFrom(), overlapPeriod.getFrom());
        Assertions.assertEquals(date2.getTo(), overlapPeriod.getTo());
    }

    @Test
    void overlap_should_return_null_if_dates_do_not_overlap() {
        TimeInterval date1 = new TimeInterval(OffsetDateTime.now(), OffsetDateTime.now().plusHours(1));
        TimeInterval date2 = new TimeInterval(OffsetDateTime.now().plusHours(1), OffsetDateTime.now().plusHours(2));

        TimeInterval overlapPeriod = date1.overlap(date2);
        Assertions.assertNull(overlapPeriod);
    }
}

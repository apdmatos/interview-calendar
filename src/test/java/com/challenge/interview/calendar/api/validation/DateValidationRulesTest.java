package com.challenge.interview.calendar.api.validation;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.OffsetDateTime;

public class DateValidationRulesTest {
    @SneakyThrows
    @Test
    void validate_should_not_throw_exception_if_dates_are_valid() {
        OffsetDateTime from = OffsetDateTime.now().plusDays(1).withMinute(0);
        OffsetDateTime to = from.plusHours(1).withMinute(0);
        DateValidationRules.validate(from, to);
    }

    @SneakyThrows
    @Test
    void validate_should_throw_exception_if_dates_are_not_valid() {
        OffsetDateTime from = OffsetDateTime.now().plusDays(1).withMinute(0);
        OffsetDateTime to = from.plusHours(1).withMinute(10);
        try {
            DateValidationRules.validate(from, to);
            Assertions.assertTrue(false, "validate should have thrown an exception");
        }catch(Exception e) {
            Assertions.assertTrue(true);
        }
    }

    @Test
    void isIntervalValid_should_return_true_if_both_dates_start_at_hour() {
        OffsetDateTime from = OffsetDateTime.parse("2022-07-14T13:00:00+01:00");
        OffsetDateTime to = OffsetDateTime.parse("2022-07-14T13:00:00+01:00");

        boolean result = DateValidationRules.isIntervalValid(from, to);
        Assertions.assertTrue(result);
    }

    @Test
    void isIntervalValid_should_return_false_if_any_date_does_not_start_at_hour() {
        OffsetDateTime from = OffsetDateTime.parse("2022-07-14T13:30:00+01:00");
        OffsetDateTime to = OffsetDateTime.parse("2022-07-14T13:00:00+01:00");

        boolean result = DateValidationRules.isIntervalValid(from, to);
        Assertions.assertFalse(result);
    }

    @Test
    void isIntervalInFuture_should_return_false_if_the_interval_is_not_in_future() {
        OffsetDateTime from = OffsetDateTime.now();
        OffsetDateTime to = OffsetDateTime.now().plusHours(1);

        boolean result = DateValidationRules.isIntervalInFuture(from, to);
        Assertions.assertFalse(result);
    }

    @Test
    void isIntervalInFuture_should_return_true_if_the_interval_is_in_future() {
        OffsetDateTime from = OffsetDateTime.now().plusHours(1);
        OffsetDateTime to = OffsetDateTime.now().plusHours(2);

        boolean result = DateValidationRules.isIntervalInFuture(from, to);
        Assertions.assertTrue(result);
    }

    @Test
    void isFromBeforeThanTo_should_return_true_if_dates_are_correct() {
        OffsetDateTime from = OffsetDateTime.now().plusHours(1);
        OffsetDateTime to = OffsetDateTime.now().plusHours(2);

        boolean result = DateValidationRules.isFromBeforeThanTo(from, to);
        Assertions.assertTrue(result);
    }

    @Test
    void isFromBeforeThanTo_should_return_false_if_to_is_before_than_from() {
        OffsetDateTime from = OffsetDateTime.now().plusHours(2);
        OffsetDateTime to = OffsetDateTime.now().plusHours(1);

        boolean result = DateValidationRules.isFromBeforeThanTo(from, to);
        Assertions.assertFalse(result);
    }
}

package com.challenge.interview.calendar.api.validation

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import java.time.OffsetDateTime

internal class DateValidationRulesTest {

    @Test
    fun validate_should_not_throw_exception_if_dates_are_valid() {
        val from = OffsetDateTime.now().plusDays(1).withMinute(0);
        val to = from.plusHours(1).withMinute(0);
        DateValidationRules.validate(from, to);
    }

    @Test
    fun validate_should_throw_exception_if_dates_are_not_valid() {
        val from = OffsetDateTime.now().plusDays(1).withMinute(0);
        val to = from.plusHours(1).withMinute(10);
        try {
            DateValidationRules.validate(from, to);
            assertTrue(false, "validate should have thrown an exception")
        }catch(e: Exception) {
            assertTrue(true);
        }
    }

    @Test
    fun isIntervalValid_should_return_true_if_both_dates_start_at_hour() {
        val from = OffsetDateTime.parse("2022-07-14T13:00:00+01:00")
        val to = OffsetDateTime.parse("2022-07-14T13:00:00+01:00")

        val result = DateValidationRules.isIntervalValid(from, to);
        assertTrue(result);
    }

    @Test
    fun isIntervalValid_should_return_false_if_any_date_does_not_start_at_hour() {
        val from = OffsetDateTime.parse("2022-07-14T13:30:00+01:00")
        val to = OffsetDateTime.parse("2022-07-14T13:00:00+01:00")

        val result = DateValidationRules.isIntervalValid(from, to);
        assertFalse(result);
    }

    @Test
    fun isIntervalInFuture_should_return_false_if_the_interval_is_not_in_future() {
        val from = OffsetDateTime.now();
        val to = OffsetDateTime.now().plusHours(1);

        val result = DateValidationRules.isIntervalInFuture(from, to);
        assertFalse(result);
    }

    @Test
    fun isIntervalInFuture_should_return_true_if_the_interval_is_in_future() {
        val from = OffsetDateTime.now().plusHours(1);
        val to = OffsetDateTime.now().plusHours(2);

        val result = DateValidationRules.isIntervalInFuture(from, to);
        assertTrue(result);
    }

    @Test
    fun isFromBeforeThanTo_should_return_true_if_dates_are_correct() {
        val from = OffsetDateTime.now().plusHours(1);
        val to = OffsetDateTime.now().plusHours(2);

        val result = DateValidationRules.isFromBeforeThanTo(from, to);
        assertTrue(result);
    }

    @Test
    fun isFromBeforeThanTo_should_return_false_if_to_is_before_than_from() {
        val from = OffsetDateTime.now().plusHours(2);
        val to = OffsetDateTime.now().plusHours(1);

        val result = DateValidationRules.isFromBeforeThanTo(from, to);
        assertFalse(result);
    }
}
package com.challenge.interview.calendar.api.dto

import java.time.OffsetDateTime

class NonIdentifiableAvailabilityDto(val from: OffsetDateTime, val to: OffsetDateTime);
class AvailabilityDto(val id: Long, val from: OffsetDateTime, val to: OffsetDateTime);
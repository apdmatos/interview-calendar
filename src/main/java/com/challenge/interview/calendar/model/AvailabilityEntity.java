package com.challenge.interview.calendar.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@AllArgsConstructor
@Data
@NoArgsConstructor
@Entity
public class AvailabilityEntity {

    public AvailabilityEntity(OffsetDateTime from, OffsetDateTime to, String calendarId, String role) {
        this.from = from;
        this.to = to;
        this.calendarId = calendarId;
        this.role = role;
    }

    @Column(name = "fromDate", nullable = false)
    private OffsetDateTime from;

    @Column(name = "toDate", nullable = false)
    private OffsetDateTime to;

    @Column(nullable = false)
    private String calendarId;

    @Column(nullable = false)
    private String role;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    public TimeInterval toTimeInterval() {
        return new TimeInterval(this.from, this.to);
    }
}

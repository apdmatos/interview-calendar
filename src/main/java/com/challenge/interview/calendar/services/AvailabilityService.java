package com.challenge.interview.calendar.services;

import com.challenge.interview.calendar.model.AvailabilityEntity;
import com.challenge.interview.calendar.model.Role;
import com.challenge.interview.calendar.model.TimeInterval;
import com.challenge.interview.calendar.repository.AvailabilityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AvailabilityService {

    private AvailabilityRepository repository;

    @Autowired
    public AvailabilityService (AvailabilityRepository repository) {
        this.repository = repository;
    }

    public List<TimeInterval> getAvailabilities(String candidateId, List<String> interviewerIds) {
        List<AvailabilityEntity> candidateAvailabilities = repository.findByCalendarIdAndRole(candidateId, Role.CANDIDATE.toString());
        List<List<AvailabilityEntity>> interviewersAvailabilities = interviewerIds.stream()
                .map((interviewerId) -> repository.findByCalendarIdAndRole(interviewerId, Role.INTERVIEWER.toString()))
                .collect(Collectors.toList());

        final List<TimeInterval>[] intersectedIntervals = (List<TimeInterval>[]) new Object[1];
        intersectedIntervals[0] = candidateAvailabilities.stream()
                .map((availability) -> availability.toTimeInterval())
                .collect(Collectors.toList());

        interviewersAvailabilities.forEach((interviewerAvailability) -> {
            List<TimeInterval> interviewerIntervals = interviewerAvailability.stream()
                    .map((availability) -> availability.toTimeInterval())
                    .collect(Collectors.toList());
            intersectedIntervals[0] = intersect(intersectedIntervals[0], interviewerIntervals);
        });

        return intersectedIntervals[0];
    }

    private List<TimeInterval> intersect(List<TimeInterval> intersectedIntervals, List<TimeInterval> interviewerIntervals) {
        ArrayList<TimeInterval> list = new ArrayList<>();
        interviewerIntervals.forEach((interval) -> {
            interviewerIntervals.forEach((interviewerTimeInterval) -> {
                TimeInterval overlapInterval = interval.overlap(interviewerTimeInterval);
                if (overlapInterval != null)
                    list.add(overlapInterval);
            });
        });
        return list;
    }
}

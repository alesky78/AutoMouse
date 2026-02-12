package it.spaghettisource.automouse.utils;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

/**
 * Represents a daily time slot [start, end] for the auto stop feature.
 * No business validation (overlap, midnight-cross) is performed here.
 *
 * Slot is inclusive of start, exclusive of end.
 */
public final class TimeSlot implements Comparable<TimeSlot> {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

    private final LocalTime start;
    private final LocalTime end;

    public TimeSlot(LocalTime start, LocalTime end) {
        this.start = Objects.requireNonNull(start, "start must not be null");
        this.end = Objects.requireNonNull(end, "end must not be null");
    }

    public LocalTime getStart() {
        return start;
    }

    public LocalTime getEnd() {
        return end;
    }

    /**
     * Returns true if 'now' is within the slot [start, end), i.e. start <= now < end.
     */
    public boolean contains(LocalTime now) {
        return (now.equals(start) || now.isAfter(start)) && now.isBefore(end);
    }

    @Override
    public int compareTo(TimeSlot other) {
        int cmp = this.start.compareTo(other.start);
        if (cmp != 0) return cmp;
        return this.end.compareTo(other.end);
    }

    @Override
    public String toString() {
        return start.format(FORMATTER) + "-" + end.format(FORMATTER);
    }

}


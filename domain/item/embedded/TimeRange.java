package com.lessonlink.domain.item.embedded;

import jakarta.persistence.Embeddable;

import java.time.LocalTime;

@Embeddable
public class TimeRange {
    private LocalTime startTime;  // 시작 시간
    private LocalTime endTime;  // 종료 시간

    public TimeRange() {
    }

    public TimeRange(LocalTime startTime, LocalTime endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }
}

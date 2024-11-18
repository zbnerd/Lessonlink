package com.lessonlink.domain.db.item.embedded;

import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.time.LocalTime;

@Embeddable
@Getter
@EqualsAndHashCode
public class TimeRange {
    private final LocalTime startTime;  // 시작 시간
    private final LocalTime endTime;  // 종료 시간

    protected TimeRange() {
        this.startTime = LocalTime.now();
        this.endTime = LocalTime.now().plusHours(1);
    }

    public TimeRange(LocalTime startTime, LocalTime endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }
}

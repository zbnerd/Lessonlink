package com.lessonlink.domain.db.item.embedded;

import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Embeddable
@Getter
@EqualsAndHashCode
public class Duration {
    private final int durationHour;  // 강의 시간 (예: 총 10시간)
    private final int durationMinute;

    protected Duration() {
        this.durationHour = 0;
        this.durationMinute = 0;
    }

    public Duration(int hour, int minute) {
        this.durationHour = hour;
        this.durationMinute = minute;
    }
}

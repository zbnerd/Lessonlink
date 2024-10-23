package com.lessonlink.domain.item.embedded;

import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Embeddable
@Getter
@EqualsAndHashCode
public class Duration {
    private int durationHour;  // 강의 시간 (예: 총 10시간)
    private int durationMinute;

    public Duration() {
    }

    public Duration(int hour, int minute) {
        this.durationHour = hour;
        this.durationMinute = minute;
    }
}

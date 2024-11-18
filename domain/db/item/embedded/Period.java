package com.lessonlink.domain.db.item.embedded;

import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.time.LocalDate;

@Embeddable
@Getter
@EqualsAndHashCode
public class Period {
    private final LocalDate startDate;  // 시작일
    private final LocalDate endDate;    // 종료일

    protected Period() {
        this.startDate = LocalDate.now();
        this.endDate = LocalDate.now().plusDays(1);
    }

    public Period(LocalDate startDate, LocalDate endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }
}

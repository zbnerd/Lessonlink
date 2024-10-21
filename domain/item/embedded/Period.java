package com.lessonlink.domain.item.embedded;

import jakarta.persistence.Embeddable;

import java.time.LocalDate;

@Embeddable
public class Period {
    private LocalDate startDate;  // 시작일
    private LocalDate endDate;    // 종료일

    public Period() {
    }

    public Period(LocalDate startDate, LocalDate endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
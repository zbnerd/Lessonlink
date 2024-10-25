package com.lessonlink.domain.attendance.embedded;

import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalTime;

@Embeddable
@Getter
@EqualsAndHashCode
public class AttendanceDateTime {
    private final LocalDate attendanceDate;  // 출석 일자
    private final LocalTime checkInTime;     // 체크인 시간
    private final LocalTime checkOutTime;    // 체크아웃 시간

    protected AttendanceDateTime() {
        this.attendanceDate = null;
        this.checkInTime = null;
        this.checkOutTime = null;
    }

    public AttendanceDateTime(LocalDate attendanceDate, LocalTime checkInTime, LocalTime checkOutTime) {
        this.attendanceDate = attendanceDate;
        this.checkInTime = checkInTime;
        this.checkOutTime = checkOutTime;
    }
}
package com.lessonlink.dto.builder;

import com.lessonlink.domain.db.attendance.enums.AttendanceStatus;
import com.lessonlink.domain.db.reservation.Reservation;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Slf4j
@Builder
public class AttendanceDto {
    private Reservation reservation;
    private LocalDate attendanceDate;
    private LocalTime checkInTime;
    private AttendanceStatus attendanceStatus;

}
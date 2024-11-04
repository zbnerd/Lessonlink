package com.lessonlink.dto;

import com.lessonlink.domain.attendance.enums.AttendanceStatus;
import com.lessonlink.domain.reservation.Reservation;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Slf4j
public class AttendanceDto {
    private Reservation reservation;
    private LocalDate attendanceDate;
    private LocalTime checkInTime;
    private AttendanceStatus attendanceStatus;

    private AttendanceDto(Builder builder) {
        this.reservation = builder.reservation;
        this.attendanceDate = builder.attendanceDate;
        this.checkInTime = builder.checkInTime;
        this.attendanceStatus = builder.attendanceStatus;
    }

    public static class Builder {
        private Reservation reservation;
        private LocalDate attendanceDate;
        private LocalTime checkInTime;
        private AttendanceStatus attendanceStatus;

        public Builder reservation(Reservation reservation) {
            this.reservation = reservation;
            return this;
        }

        public Builder attendanceDate(LocalDate attendanceDate) {
            this.attendanceDate = attendanceDate;
            return this;
        }

        public Builder checkInTime(LocalTime checkInTime) {
            this.checkInTime = checkInTime;
            return this;
        }

        public Builder attendanceStatus(AttendanceStatus attendanceStatus) {
            this.attendanceStatus = attendanceStatus;
            return this;
        }

        public AttendanceDto build() {
            return new AttendanceDto(this);
        }
    }
}
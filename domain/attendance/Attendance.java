package com.lessonlink.domain.attendance;

import com.lessonlink.domain.attendance.embedded.AttendanceDateTime;
import com.lessonlink.domain.attendance.enums.AttendanceStatus;
import com.lessonlink.domain.common.BaseTimeEntity;
import com.lessonlink.domain.reservation.Reservation;
import com.lessonlink.dto.AttendanceDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;

@Entity
@Getter
public class Attendance extends BaseTimeEntity {
    @Id @GeneratedValue
    @Column(name = "attendance_id")
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reservation_id")
    private Reservation reservation;

    @Embedded
    private AttendanceDateTime attendanceDateTime;

    @Enumerated(EnumType.STRING)
    @Setter
    private AttendanceStatus attendanceStatus;

    public void setAttendanceInfo(AttendanceDto attendanceDto) {
        this.reservation = attendanceDto.getReservation();
        this.attendanceStatus = attendanceDto.getAttendanceStatus();

        // AttendanceDateTime을 새로 생성하여 설정
        this.attendanceDateTime = new AttendanceDateTime(
                attendanceDto.getAttendanceDate(),
                attendanceDto.getCheckInTime(),
                null
        );
    }

    //==비즈니스 로직==//
    /** 출석체크 **/
    public void checkPresent() {
        this.setAttendanceStatus(AttendanceStatus.PRESENT);
    }

    /** 지각 **/
    public void checkLate() {
        this.setAttendanceStatus(AttendanceStatus.LATE);
    }

    /** 결석 **/
    public void checkAbsent() {
        this.setAttendanceStatus(AttendanceStatus.ABSENT);
    }

    /** 체크아웃 **/
    public void checkOut() {
        this.attendanceDateTime.setCheckOutTime(LocalTime.now().withNano(0));
    }
}

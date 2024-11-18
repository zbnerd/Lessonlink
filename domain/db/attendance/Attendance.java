package com.lessonlink.domain.db.attendance;

import com.lessonlink.domain.db.attendance.embedded.AttendanceDateTime;
import com.lessonlink.domain.db.attendance.enums.AttendanceStatus;
import com.lessonlink.domain.db.common.BaseTimeEntity;
import com.lessonlink.domain.db.reservation.Reservation;
import com.lessonlink.domain.db.reservation.enums.ReservationStatus;
import com.lessonlink.dto.builder.AttendanceDto;
import com.lessonlink.exception.AttendanceUpdateNotAllowedException;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalTime;

@Entity
@Getter
@ToString
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
        validateReservationStatus();
        this.setAttendanceStatus(AttendanceStatus.PRESENT);
    }

    /** 지각 **/
    public void checkLate() {
        validateReservationStatus();
        this.setAttendanceStatus(AttendanceStatus.LATE);
    }

    /** 결석 **/
    public void checkAbsent() {
        validateReservationStatus();
        this.setAttendanceStatus(AttendanceStatus.ABSENT);
    }

    private void validateReservationStatus() {
        if (reservation.getReservationStatus() == ReservationStatus.CANCELED) {
            throw new AttendanceUpdateNotAllowedException("예약이 취소된 건에 관한 출결은 변경할 수 없습니다.");
        }
    }

    /** 체크아웃 **/
    public void checkOut() {
        this.attendanceDateTime.setCheckOutTime(LocalTime.now().withNano(0));
    }
}

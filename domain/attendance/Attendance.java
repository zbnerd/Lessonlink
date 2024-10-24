package com.lessonlink.domain.attendance;

import com.lessonlink.domain.attendance.embedded.AttendanceDateTime;
import com.lessonlink.domain.attendance.enums.AttendanceStatus;
import com.lessonlink.domain.common.BaseTimeEntity;
import com.lessonlink.domain.reservation.Reservation;
import jakarta.persistence.*;
import lombok.Getter;

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
    private AttendanceStatus attendanceStatus;
}

package com.lessonlink.service;

import com.lessonlink.domain.attendance.Attendance;
import com.lessonlink.domain.attendance.enums.AttendanceStatus;
import com.lessonlink.domain.reservation.Reservation;
import com.lessonlink.dto.AttendanceDto;
import com.lessonlink.repository.AttendanceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AttendanceService {

    private final AttendanceRepository attendanceRepository;
    private final ReservationService reservationService;


    @Transactional
    public Long checkIn(Long reservationId) {

        Reservation reservation = reservationService.findOne(reservationId);

        Attendance attendance = new Attendance();
        attendance.setAttendanceInfo(
                new AttendanceDto.Builder()
                        .reservation(reservation)
                        .attendanceDate(LocalDate.now())
                        .checkInTime(LocalTime.now().withNano(0))
                        .attendanceStatus(AttendanceStatus.PRESENT)
                        .build()
        );
        Attendance saved = attendanceRepository.save(attendance);
        return saved.getId();
    }

    public Attendance findOne(Long attendanceId) {
        return attendanceRepository.findById(attendanceId)
                .orElseThrow(() -> new IllegalArgumentException("해당 출석정보가 존재하지 않습니다. ID: " + attendanceId));
    }

    @Transactional
    public void updateAttendanceStatusToPresent(Long id) {
        Optional<Attendance> foundAttendance = attendanceRepository.findById(id);
        if (foundAttendance.isPresent()) {
            Attendance attendance = foundAttendance.get();
            attendance.checkPresent();
        }
    }

    @Transactional
    public void updateAttendanceStatusToLate(Long id) {
        Optional<Attendance> foundAttendance = attendanceRepository.findById(id);
        if (foundAttendance.isPresent()) {
            Attendance attendance = foundAttendance.get();
            attendance.checkLate();
        }
    }

    @Transactional
    public void updateAttendanceStatusToAbsent(Long id) {
        Optional<Attendance> foundAttendance = attendanceRepository.findById(id);
        if (foundAttendance.isPresent()) {
            Attendance attendance = foundAttendance.get();
            attendance.checkAbsent();
        }
    }

    @Transactional
    public void checkOut(Long attendanceId) {
        Optional<Attendance> foundAttendance = attendanceRepository.findById(attendanceId);
        if (foundAttendance.isPresent()) {
            Attendance attendance = foundAttendance.get();
            attendance.checkOut();
        }
    }

}

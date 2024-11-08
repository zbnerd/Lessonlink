package com.lessonlink.service;

import com.lessonlink.domain.attendance.Attendance;
import com.lessonlink.domain.attendance.enums.AttendanceStatus;
import com.lessonlink.domain.reservation.Reservation;
import com.lessonlink.dto.AttendanceDto;
import com.lessonlink.exception.NotFoundException;
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
                .orElseThrow(() -> new NotFoundException("해당 출석정보가 존재하지 않습니다. ID: " + attendanceId));
    }

    @Transactional
    public void updateAttendanceStatusToPresent(Long attendanceId) {
        Attendance foundAttendance = findOne(attendanceId);
        foundAttendance.checkPresent();
    }

    @Transactional
    public void updateAttendanceStatusToLate(Long attendanceId) {
        Attendance foundAttendance = findOne(attendanceId);
        foundAttendance.checkLate();
    }

    @Transactional
    public void updateAttendanceStatusToAbsent(Long attendanceId) {
        Attendance foundAttendance = findOne(attendanceId);
        foundAttendance.checkAbsent();
    }

    @Transactional
    public void checkOut(Long attendanceId) {
        Attendance foundAttendance = findOne(attendanceId);
        foundAttendance.checkOut();
    }

    @Transactional
    public void deleteAttendance(Long attendanceId) {
        Attendance foundAttendance = findOne(attendanceId);
        attendanceRepository.delete(foundAttendance);
    }
}

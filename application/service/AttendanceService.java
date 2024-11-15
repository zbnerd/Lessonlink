package com.lessonlink.application.service;

import com.lessonlink.domain.attendance.Attendance;
import com.lessonlink.domain.attendance.enums.AttendanceStatus;
import com.lessonlink.domain.reservation.Reservation;
import com.lessonlink.domain.reservation.enums.ReservationStatus;
import com.lessonlink.dto.AttendanceDto;
import com.lessonlink.dto.StudentAttendanceInfoDto;
import com.lessonlink.exception.AttendanceCreateNotAllowedException;
import com.lessonlink.exception.NotFoundException;
import com.lessonlink.repository.AttendanceRepository;
import com.lessonlink.repository.AttendanceRepositoryCustomImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AttendanceService {

    private final AttendanceRepository attendanceRepository;
    private final ReservationService reservationService;
    private final AttendanceRepositoryCustomImpl attendanceRepositoryCustomImpl;


    @Transactional
    public Long checkIn(Long reservationId) {

        Reservation reservation = reservationService.findOne(reservationId);

        validateReservationStatus(reservation);

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

    public List<StudentAttendanceInfoDto> getStudentAttendanceInfo(Long courseId) {
        return attendanceRepositoryCustomImpl.findStudentAttendanceInfoByCourseId(courseId);
    }

    private void validateReservationStatus(Reservation reservation) {
        if (reservation.getReservationStatus() == ReservationStatus.CANCELED) {
            throw new AttendanceCreateNotAllowedException("예약이 취소된 건은 출결정보에 등록할 수 없습니다.");
        }
    }
}

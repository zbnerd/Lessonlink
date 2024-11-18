package com.lessonlink.repository.attendance;

import com.lessonlink.dto.info.StudentAttendanceInfoDto;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.lessonlink.domain.attendance.QAttendance.attendance;
import static com.lessonlink.domain.item.QCourse.course;
import static com.lessonlink.domain.reservation.QReservation.reservation;

@Repository
public class AttendanceRepositoryCustomImpl implements AttendanceRepositoryCustom {

    private final EntityManager em;
    private final JPAQueryFactory query;

    public AttendanceRepositoryCustomImpl(EntityManager em) {
        this.em = em;
        this.query = new JPAQueryFactory(em);
    }

    public List<StudentAttendanceInfoDto> findStudentAttendanceInfoByCourseId(Long courseId) {
        return query
                .select(Projections.constructor(StudentAttendanceInfoDto.class,
                        reservation.studentMemberIdSecretKey,
                        attendance.attendanceStatus,
                        attendance.attendanceDateTime,
                        course.name))
                .from(course)
                .join(course.reservations, reservation)
                .join(reservation.attendances, attendance)
                .where(course.id.eq(courseId))
                .fetch();
    }
}

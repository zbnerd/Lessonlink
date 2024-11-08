package com.lessonlink.domain.reservation;

import com.lessonlink.domain.common.BaseTimeEntity;
import com.lessonlink.domain.item.Course;
import com.lessonlink.domain.member.Member;
import com.lessonlink.domain.reservation.enums.ReservationStatus;
import com.lessonlink.dto.ReservationDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
public class Reservation extends BaseTimeEntity {

    @Id @GeneratedValue @Column(name = "reservation_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Course course;

    @Setter
    private String studentMemberIdSecretKey; // 서브쿼리로 조회하기.
    private LocalDate reservationDate;

    @Enumerated(EnumType.STRING)
    @Setter
    private ReservationStatus reservationStatus;

    public void setReservationInfo(ReservationDto reservationDto) {
        this.studentMemberIdSecretKey = reservationDto.getStudentMemberIdSecretKey();
        this.reservationDate = reservationDto.getReservationDate();
        this.reservationStatus = ReservationStatus.RESERVED;
    }

    //== 생성 메서드 ==//
    public static Reservation createReservation(Member member, Course course, ReservationDto reservationDto) {
        Reservation reservation = new Reservation();
        reservation.setStudentMemberIdSecretKey(member.getId());
        reservation.setCourse(course);

        reservation.setReservationInfo(reservationDto);

        return reservation;
    }

    //== 연관관계 메서드 ==//
    public void setCourse(Course course) {
        this.course = course;
        course.getReservations().add(this);
    }

    //==비즈니스 로직==//
    /** 예약 취소 **/
    public void cancel() {
        this.setReservationStatus(ReservationStatus.CANCELED);
    }
}

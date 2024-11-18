package com.lessonlink.domain.db.reservation;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lessonlink.domain.db.attendance.Attendance;
import com.lessonlink.domain.db.common.BaseTimeEntity;
import com.lessonlink.domain.db.item.Course;
import com.lessonlink.domain.db.member.Member;
import com.lessonlink.domain.db.reservation.enums.ReservationStatus;
import com.lessonlink.dto.builder.ReservationDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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

    @JsonIgnore
    @ToString.Exclude
    @OneToMany(mappedBy = "reservation")
    private List<Attendance> attendances = new ArrayList<>();

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

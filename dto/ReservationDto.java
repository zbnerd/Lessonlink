package com.lessonlink.dto;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;

/**
 * 수업 예약 정보를 담고 있는 `ReservationDto` 클래스.
 * 빌더 패턴을 사용하여 예약 객체를 생성하며, 수업 ID, 학생의 비밀키 ID, 예약일,
 * 예약 상태를 설정할 수 있습니다.
 * 클래스 내부에는 `Builder` 클래스가 있어 각 필드를 설정한 후
 * `build()` 메서드를 통해 `ReservationDto` 객체를 생성할 수 있습니다.
 */
@Getter
@Slf4j
public class ReservationDto {
    private String studentMemberIdSecretKey;
    private LocalDate reservationDate;

    // `Builder` 클래스에서 전달된 값을 통해 `ReservationDto` 객체를 생성하는 생성자
    private ReservationDto(Builder builder) {
        this.studentMemberIdSecretKey = builder.studentMemberIdSecretKey;
        this.reservationDate = builder.reservationDate;
    }

    /**
     * 예약 정보를 설정하는 빌더 클래스.
     * 빌더 패턴을 통해 각 필드를 설정하고, `build()` 메서드를 통해
     * `ReservationDto` 객체를 생성합니다.
     */
    public static class Builder {
        private String studentMemberIdSecretKey;
        private LocalDate reservationDate;

        public Builder studentMemberIdSecretKey(String studentMemberIdSecretKey) {
            this.studentMemberIdSecretKey = studentMemberIdSecretKey;
            return this;
        }

        public Builder reservationDate(LocalDate reservationDate) {
            this.reservationDate = reservationDate;
            return this;
        }

        public ReservationDto build() {
            return new ReservationDto(this);
        }
    }
}
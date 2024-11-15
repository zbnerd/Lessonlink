package com.lessonlink.adapter.in.api.reservation;

import com.lessonlink.adapter.in.api.Result;
import com.lessonlink.domain.member.Member;
import com.lessonlink.domain.reservation.Reservation;
import com.lessonlink.domain.reservation.enums.ReservationStatus;
import com.lessonlink.repository.ReservationRepository;
import com.lessonlink.application.service.ReservationService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.IntStream;

@RestController
@RequiredArgsConstructor
public class ReservationApiController {

    private final ReservationService reservationService;
    private final ReservationRepository reservationRepository;

    /**
     * 특정 강의에 대한 모든 예약 정보를 조회합니다.
     * @param courseId 조회할 강의의 ID
     * @param pageable 페이지네이션 정보
     * @return 강의별 예약 정보 목록
     */
    @GetMapping("/api/v1/courses/{courseId}/reservations")
    public Result allReservationsByCourse(
            @PathVariable Long courseId,
            Pageable pageable
    ) {
        List<Reservation> reservations = reservationRepository.findByCourseId(courseId, pageable);
        List<Member> members = reservationService.findReservedStudentsByCourse(courseId, pageable);

        return new Result(
                IntStream.range(0, reservations.size())
                        .mapToObj(i ->
                                new ReservationResponseDto(reservations.get(i), members.get(i)))
                        .toList()
        );
    }

    /**
     * 예약을 생성합니다.
     * @param request 예약 요청 데이터를 포함하는 DTO
     * @return 생성된 예약의 ID 리스트와 예약 상태
     */
    @PostMapping("/api/v1/reservations")
    public List<MakeReservationResponseDto> makeReservation(
            @RequestBody MakeReservationRequestDto request
    ) {
        List<Long> reservationIdList = reservationService.makeReservation(request.orderId);

        return reservationIdList.stream()
                .map(MakeReservationResponseDto::new)
                .toList();
    }

    /**
     * 예약 정보를 응답하는 DTO 클래스입니다.
     */
    @Data
    class ReservationResponseDto {
        private String courseName;
        private String memberName;
        private LocalDate reservationDate;
        private ReservationStatus reservationStatus;

        public ReservationResponseDto(Reservation reservation, Member member) {
            this.courseName = reservation.getCourse().getName();
            this.memberName = member.getName();
            this.reservationDate = reservation.getReservationDate();
            this.reservationStatus = reservation.getReservationStatus();
        }
    }

    /**
     * 예약 요청 데이터를 포함하는 DTO 클래스입니다.
     */
    @Data
    static class MakeReservationRequestDto {
        private Long orderId;
    }

    /**
     * 예약 생성에 대한 응답 데이터를 포함하는 DTO 클래스입니다.
     */
    @Data
    class MakeReservationResponseDto {
        private Long reservationId;
        private ReservationStatus reservationStatus;

        public MakeReservationResponseDto(Long reservationId) {
            this.reservationId = reservationId;
            this.reservationStatus = reservationService.findOne(reservationId).getReservationStatus();
        }
    }
}
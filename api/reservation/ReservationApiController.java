package com.lessonlink.api.reservation;

import com.lessonlink.domain.member.Member;
import com.lessonlink.domain.reservation.Reservation;
import com.lessonlink.domain.reservation.enums.ReservationStatus;
import com.lessonlink.repository.ReservationRepository;
import com.lessonlink.service.ReservationService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.IntStream;

@RestController
@RequiredArgsConstructor
public class ReservationApiController {

    private final ReservationService reservationService;
    private final ReservationRepository reservationRepository;

    @GetMapping("/api/v1/courses/{courseId}/reservations")
    public Result allReservationsByCourse(
            @PathVariable Long courseId
    ) {

        List<Reservation> reservations = reservationRepository.findByCourseId(courseId);
        List<Member> members = reservationService.findReservedStudentsByCourse(courseId);

        return new Result(
                IntStream.range(0, reservations.size())
                        .mapToObj(i ->
                                new ReservationResponseDto(reservations.get(i), members.get(i)))
                        .toList()
        );
    }

    @PostMapping("/api/v1/reservations")
    public List<MakeReservationResponseDto> makeReservation(
            @RequestBody MakeReservationRequestDto request
    ) {
        List<Long> reservationIdList = reservationService.makeReservation(request.memberIdSecretKey, request.orderId);

        return reservationIdList.stream()
                .map(MakeReservationResponseDto::new)
                .toList();
    }


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

    @Data
    static class MakeReservationRequestDto {
        private String memberIdSecretKey;
        private Long orderId;
    }

    @Data
    class MakeReservationResponseDto {
        private Long reservationId;
        private ReservationStatus reservationStatus;

        public MakeReservationResponseDto(Long reservationId) {
            this.reservationId = reservationId;
            this.reservationStatus = reservationService.findOne(reservationId).getReservationStatus();
        }
    }

    @Data
    @AllArgsConstructor
    static class Result<T> {
        private T data;
    }
}

package com.lessonlink.application.service;

import com.lessonlink.domain.item.Course;
import com.lessonlink.domain.member.Member;
import com.lessonlink.domain.order.Order;
import com.lessonlink.domain.order.OrderItem;
import com.lessonlink.domain.order.enums.OrderStatus;
import com.lessonlink.domain.reservation.Reservation;
import com.lessonlink.dto.builder.ReservationDto;
import com.lessonlink.exception.NotFoundException;
import com.lessonlink.exception.ReservationNotAllowedException;
import com.lessonlink.repository.order.OrderRepositoryCustomImpl;
import com.lessonlink.repository.reservation.ReservationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class ReservationService {

    private final OrderRepositoryCustomImpl orderRepositoryCustomImpl;
    private final ReservationRepository reservationRepository;
    private final MemberService memberService;

    @Transactional
    public List<Long> makeReservation(Long orderId) {
        List<Order> orders = orderRepositoryCustomImpl.findOrdersByOrderId(orderId);

        List<Long> reservationIds = new ArrayList<>();

        for (Order order : orders) {

            if (order.getStatus() == OrderStatus.CANCEL) {
                throw new ReservationNotAllowedException("취소된 주문상품은 예약할 수 없습니다.");
            }

            for (OrderItem orderItem : order.getOrderItems()) {

                if (orderItem.getItem() instanceof Course course) {

                    ReservationDto reservationDto = ReservationDto.builder()
                            .studentMemberIdSecretKey(order.getMember().getId())
                            .reservationDate(LocalDate.now())
                            .build();

                    Reservation reservation = Reservation.createReservation(order.getMember(), course, reservationDto);

                reservationIds.add(reservationRepository.save(reservation).getId());
                }
            }
        }

        return reservationIds;
    }

    public List<Member> findReservedStudentsByCourse(Long courseId, Pageable pageable) {
        List<Reservation> reservations = reservationRepository.findByCourseId(courseId, pageable);
        return reservations.stream()
                .map((r) -> memberService.findOne(r.getStudentMemberIdSecretKey()))
                .toList();
    }

    public Reservation findOne(Long reservationId) {
        return reservationRepository.findById(reservationId)
                .orElseThrow(() -> new NotFoundException("해당 예약정보가 존재하지 않습니다. ID: " + reservationId));
    }

    /** 예약 취소 **/

    @Transactional
    public void cancelReservation(Long reservationId) {
        Reservation reservation = findOne(reservationId);
        reservation.cancel();
    }

    /** 예약 삭제 */
    @Transactional
    public void deleteReservation(Long reservationId) {
        Reservation reservation = findOne(reservationId);
        reservationRepository.delete(reservation);
    }

}

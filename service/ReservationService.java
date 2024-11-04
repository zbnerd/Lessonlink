package com.lessonlink.service;

import com.lessonlink.domain.item.Course;
import com.lessonlink.domain.member.Member;
import com.lessonlink.domain.order.Order;
import com.lessonlink.domain.order.OrderItem;
import com.lessonlink.domain.order.enums.OrderStatus;
import com.lessonlink.domain.reservation.Reservation;
import com.lessonlink.dto.ReservationDto;
import com.lessonlink.exception.NotFoundException;
import com.lessonlink.repository.OrderRepository;
import com.lessonlink.repository.ReservationRepository;
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

    private final OrderRepository orderRepository;
    private final ReservationRepository reservationRepository;
    private final MemberService memberService;

    @Transactional
    public List<Long> makeReservation(Long orderId) {
        List<Order> orders = orderRepository.findOrdersByOrderId(orderId);

        List<Long> reservationIds = new ArrayList<>();

        for (Order order : orders) {

            if (order.getStatus() == OrderStatus.CANCEL) {
                throw new IllegalStateException("취소된 주문상품은 예약할 수 없습니다.");
            }

            for (OrderItem orderItem : order.getOrderItems()) {

                if (orderItem.getItem() instanceof Course course) {
                    Reservation reservation = new Reservation();
                    reservation.setReservationInfo(
                        new ReservationDto.Builder()
                                .course(course)
                                .studentMemberIdSecretKey(order.getMember().getId())
                                .reservationDate(LocalDate.now())
                                .build()
                );
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

    @Transactional
    public void cancelReservation(Long reservationId) {
        Reservation reservation = findOne(reservationId);
        reservation.cancel();
    }

}

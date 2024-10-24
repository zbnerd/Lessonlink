package com.lessonlink.service;

import com.lessonlink.domain.item.Course;
import com.lessonlink.domain.member.Member;
import com.lessonlink.domain.order.Order;
import com.lessonlink.domain.order.OrderItem;
import com.lessonlink.domain.reservation.Reservation;
import com.lessonlink.dto.ReservationDto;
import com.lessonlink.repository.OrderRepository;
import com.lessonlink.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReservationService {

    private final OrderRepository orderRepository;
    private final ReservationRepository reservationRepository;
    private final MemberService memberService;

    @Transactional
    public List<Long> makeReservation(String memberIdSecretKey, Long orderId) {
        List<Order> orders = orderRepository.findOrdersByOrderId(orderId);
        List<Long> reservationIds = new ArrayList<>();

        for (Order order : orders) {
            for (OrderItem orderItem : order.getOrderItems()) {
                if (orderItem.getItem() instanceof Course course) {
                    Reservation reservation = new Reservation();
                reservation.setReservationInfo(
                        new ReservationDto.Builder()
                                .course(course)
                                .studentMemberIdSecretKey(memberIdSecretKey)
                                .reservationDate(LocalDate.now())
                                .build()
                );
                reservationIds.add(reservationRepository.save(reservation).getId());
                }
            }
        }

        return reservationIds;
    }

    public List<Member> findReservedStudentsByCourse(Long courseId) {
        List<Reservation> reservations = reservationRepository.findByCourseId(courseId);
        return reservations.stream()
                .map((r) -> memberService.findOne(r.getStudentMemberIdSecretKey()))
                .toList();
    }

    public Reservation findOne(Long reservationId) {
        return reservationRepository.findById(reservationId)
                .orElseThrow(() -> new IllegalArgumentException("해당 예약정보가 존재하지 않습니다. ID: " + reservationId));
    }

    public void cancelReservation(Long reservationId) {
        Reservation reservation = findOne(reservationId);
        reservation.cancel();
    }

}

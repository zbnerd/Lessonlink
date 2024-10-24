package com.lessonlink.service;

import com.lessonlink.domain.item.Course;
import com.lessonlink.domain.member.Member;
import com.lessonlink.domain.order.Order;
import com.lessonlink.domain.order.OrderItem;
import com.lessonlink.domain.reservation.Reservation;
import com.lessonlink.dto.ReservationDto;
import com.lessonlink.repository.OrderRepositoryCustomImpl;
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

    private final OrderRepositoryCustomImpl orderRepositoryImpl;
    private final ReservationRepository reservationRepository;
    private final MemberService memberService;

    @Transactional
    public List<Long> makeReservation(String memberIdSecretKey, Long orderId) {
        List<Order> orders = orderRepositoryImpl.findItemIdsByOrderId(orderId);
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

    public List<Member> findReservedStudentsByCourse(Course course) {
        List<Reservation> reservations = reservationRepository.findByCourse(course);
        return reservations.stream()
                .map((r) -> memberService.findOne(r.getStudentMemberIdSecretKey()))
                .toList();
    }

    public Reservation findOne(Long reservationId) {
        return reservationRepository.findById(reservationId).orElse(null);
    }

    public void cancelReservation(Long reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId).orElse(null);
        if (reservation != null) {
            reservation.cancel();
        }
    }

}

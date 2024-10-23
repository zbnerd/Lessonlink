package com.lessonlink.service;

import com.lessonlink.domain.item.Course;
import com.lessonlink.domain.item.Item;
import com.lessonlink.domain.member.Member;
import com.lessonlink.domain.reservation.Reservation;
import com.lessonlink.dto.ReservationDto;
import com.lessonlink.repository.MemberRepository;
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
        List<Item> items = orderRepository.findItemIdsByOrderId(orderId);
        List<Long> reservationIds = new ArrayList<>();

        for (Item item : items) {
            if (item instanceof Course course) {
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

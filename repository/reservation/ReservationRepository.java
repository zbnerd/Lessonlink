package com.lessonlink.repository.reservation;

import com.lessonlink.domain.reservation.Reservation;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findByCourseId(Long courseId, Pageable pageable);
}
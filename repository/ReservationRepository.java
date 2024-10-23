package com.lessonlink.repository;

import com.lessonlink.domain.item.Course;
import com.lessonlink.domain.reservation.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findByCourse(Course course);
}
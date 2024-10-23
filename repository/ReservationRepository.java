package com.lessonlink.repository;

import com.lessonlink.domain.reservation.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ReservationRepository extends JpaRepository<Reservation, Long> {}
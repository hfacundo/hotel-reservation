package com.hotel.app.repository;

import com.hotel.app.model.Reservation;

import java.util.Optional;
import java.util.Set;

public interface ReservationRepository {

    Reservation create(Reservation reservation);

    Reservation update(Reservation reservation);

    Optional<Reservation> findById(int reservationId);

    Set<Reservation> findAll();
}

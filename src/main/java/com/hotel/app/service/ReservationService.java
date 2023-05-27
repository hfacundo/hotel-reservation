package com.hotel.app.service;

import com.hotel.app.exceptions.ReservationException;
import com.hotel.app.model.Reservation;

import java.util.Set;

public interface ReservationService {

    Reservation createReservation(Reservation reservation) throws ReservationException;

    Reservation updateReservation(int reservationId, Reservation reservation) throws ReservationException;

    Set<Reservation> getAllReservations();

}

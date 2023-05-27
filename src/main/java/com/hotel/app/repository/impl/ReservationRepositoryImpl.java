package com.hotel.app.repository.impl;

import com.hotel.app.model.Reservation;
import com.hotel.app.repository.ReservationRepository;
import com.hotel.app.util.DataUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

/**
 * This class handles create, update and find reservations
 */
@Repository
public class ReservationRepositoryImpl implements ReservationRepository {

    @Autowired
    private DataUtil dataUtil;

    /**
     * Sets an unique reservationId and saves the record
     *
     * @param reservation
     * @return Reservation
     */
    @Override
    public Reservation create(Reservation reservation) {
        reservation.setId(dataUtil.getReservationId());
        return dataUtil.createReservation(reservation);
    }

    /**
     * Update a reservation
     *
     * @param reservation
     * @return
     */
    @Override
    public Reservation update(Reservation reservation) {
        return dataUtil.updateReservation(reservation);
    }

    /**
     * Search for a reservation given an id
     *
     * @param id
     * @return Optional<Reservation>
     */
    @Override
    public Optional<Reservation> findById(int id) {
        Set<Reservation> reservations = dataUtil.getAllReservations();
        Optional<Reservation> optional = reservations.stream()
                .filter(reservation -> reservation.getId() == id)
                .findFirst();
        return optional;
    }

    /**
     * Find and return all the reservations in the hotel
     *
     * @return
     */
    @Override
    public Set<Reservation> findAll() {
        return dataUtil.getAllReservations();
    }
}

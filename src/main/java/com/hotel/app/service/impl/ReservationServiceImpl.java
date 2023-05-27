package com.hotel.app.service.impl;

import com.hotel.app.exceptions.ReservationException;
import com.hotel.app.model.Reservation;
import com.hotel.app.model.Room;
import com.hotel.app.repository.ReservationRepository;
import com.hotel.app.repository.RoomRepository;
import com.hotel.app.service.ReservationService;
import com.hotel.app.util.DataUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static com.hotel.app.util.ErrorConstants.NO_ROOMS_AVAILABLE;
import static com.hotel.app.util.ErrorConstants.RESERVATION_NOT_FOUND;
import static com.hotel.app.util.ErrorConstants.ROOM_UNAVAILABLE;
import static com.hotel.app.util.ErrorConstants.WRONG_DATES;

@Service
public class ReservationServiceImpl implements ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private DataUtil dataUtil;

    @PostConstruct
    public void init() {
        dataUtil.initReservations();
    }

    /**
     * Business logic to create a reservation
     *
     * @param reservation
     * Fields provided by API client:
     *      fromDate
     *      toDate
     *      clientFullName
     * @return Reservation
     * @throws ReservationException
     */
    @Override
    public Reservation createReservation(Reservation reservation) throws ReservationException {

        // When empty it means that all rooms from "fromDate" to "toDate" are occupied
        List<Room> availableRooms = roomRepository.findRoomsAvailableByDate(reservation.getFromDate(), reservation.getToDate());
        if (CollectionUtils.isEmpty(availableRooms)) {
            throw new ReservationException(String.format(NO_ROOMS_AVAILABLE, reservation.getFromDate().toString(), reservation.getToDate().toString()));
        }

        // If true it means that reservation "fromDate" is in the past or "fromDate" and "toDate" are not valid
        if (reservation.getFromDate().isBefore(LocalDate.now()) || reservation.getFromDate().isAfter(reservation.getToDate())) {
            throw new ReservationException(WRONG_DATES);
        }

        // Setting reservation number and room number to Reservation object
        reservation.setReservationNumber(dataUtil.getReservationNumber());
        reservation.setRoomNumber(availableRooms.get(0).getRoomNumber());
        return reservationRepository.create(reservation);
    }

    /**
     *
     * @param id - id of the reservation to update
     * @param reservation - contains the new attributes to update
     * @return Reservation
     * @throws ReservationException
     */
    @Override
    public Reservation updateReservation(int id, Reservation reservation) throws ReservationException {
        Optional<Reservation> optional = reservationRepository.findById(id);
        // If reservation is found and its id does not match provided id we throw an exception
        // id provided and id found are not the same
        if (optional.isEmpty() || reservation.getId() != id)
            throw new ReservationException(String.format(RESERVATION_NOT_FOUND, id));

        Reservation originalReservation = optional.get();
        // validating dates
        if (reservation.getFromDate().isAfter(reservation.getToDate())) {
            throw new ReservationException(WRONG_DATES);
        }

        // validate when room number changed
        // and new room is available
        boolean isRoomAvailable = roomRepository.isRoomAvailableByDate(reservation.getRoomNumber(), reservation.getFromDate(), reservation.getToDate());
        if (originalReservation.getRoomNumber() != reservation.getRoomNumber()
                && !isRoomAvailable) {
            throw new ReservationException(ROOM_UNAVAILABLE);
        }
        return reservationRepository.update(reservation);
    }

    /**
     * Return a collection with all the reservations
     *
     * @return Set<Reservation>
     */
    @Override
    public Set<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }
}

package com.hotel.app.repository.impl;

import com.hotel.app.model.Reservation;
import com.hotel.app.model.Room;
import com.hotel.app.repository.ReservationRepository;
import com.hotel.app.repository.RoomRepository;
import com.hotel.app.util.DataUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

/**
 * Given that there are 3 rooms available in the hotel I created this class to
 * help to validate when all rooms are unavailable for a date range
 *
 * In case a room have more than just a room number this class will be more helpful to find rooms by different fields
 *
 */
@Repository
public class RoomRepositoryImpl implements RoomRepository {

    @Autowired
    private DataUtil dataUtil;

    @Autowired
    private ReservationRepository reservationRepository;

    /**
     * Find all the rooms available given a date range
     *
     * @param newFromDate - date when client is planning to check in
     * @param newToDate - date when client is planning to check out
     * @return List<Room> availableRooms
     */
    @Override
    public List<Room> findRoomsAvailableByDate(LocalDate newFromDate, LocalDate newToDate) {
        List<Room> rooms = dataUtil.getAllRooms();
        Set<Reservation> reservations = reservationRepository.findAll();

        for (Reservation reservation : reservations) {
            // Identify all rooms that are occupied between newFromDate and newToDate
            if (!isValidDateRange(newFromDate, newToDate, reservation.getFromDate(), reservation.getToDate())) {
                // room is taken
                Room roomToRemove = new Room(reservation.getRoomNumber());
                // occupied rooms are removed from the response
                rooms.remove(roomToRemove);
            }
        }

        return rooms;
    }

    /**
     * Check if a room is available given a date range
     *
     * @param roomNumber
     * @param fromDate
     * @param toDate
     * @return
     */
    @Override
    public Boolean isRoomAvailableByDate(int roomNumber, LocalDate fromDate, LocalDate toDate) {
        Set<Reservation> reservations = reservationRepository.findAll();

        for (Reservation reservation : reservations) {
            if (reservation.getRoomNumber() != roomNumber)
                continue;
            if (!isValidDateRange(fromDate, toDate, reservation.getFromDate(), reservation.getToDate())) {
                // room is taken
                return false;
            }
        }
        return true;
    }

    /**
     * Validate if client check in and client check out date is valid for a reservation or update
     *
     * @param fromDate
     * @param toDate
     * @param reservationFromDate
     * @param reservationToDate
     * @return
     */
    protected boolean isValidDateRange(LocalDate fromDate, LocalDate toDate, LocalDate reservationFromDate, LocalDate reservationToDate) {
        if (fromDate.isBefore(reservationFromDate)) {
            return toDate.isBefore(reservationFromDate) || toDate.isEqual(reservationFromDate);
        } else if (fromDate.isAfter(reservationFromDate)) {
            return fromDate.isAfter(reservationToDate) || fromDate.isEqual(reservationToDate);
        } else {
            // targetFrom && fromDate are equals
            return false;
        }
    }
}

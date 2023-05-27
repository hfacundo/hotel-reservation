package com.hotel.app.util;

import com.hotel.app.model.Reservation;
import com.hotel.app.model.Room;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

/**
 * Class where we are simulating extracting information from a database
 *
 * No real connection will be processed
 */
@Component
public class DataUtil {

    // In order to make the API easier to test we have only 3 rooms available
    private List<Room> rooms = List.of(new Room(101), new Room(102), new Room(103));
    // In this collection we are saving reservation when created
    private Set<Reservation> reservations;
    // Initial value for a reservation, it will be increased by 1 every time a new reservation is created
    private int reservationId = 1;
    // Initial reservation number, it will be increased by 1 every time a new reservation is created
    private int reservationNumber = 1001;

    public void initReservations() {
        reservations = new TreeSet<>();
    }

    /**
     * Return the current reservation number converted to a String
     *
     * @return String
     */
    public String getReservationNumber() {
        int number = reservationNumber;
        reservationNumber++;
        return String.valueOf(number);
    }

    /**
     * Return the current reservation id
     * This is a helper method to simulates an autoincrement id in a database
     *
     * @return int
     */
    public int getReservationId() {
        int id = reservationId;
        reservationId++;
        return id;
    }

    /**
     * Return a list with all the available rooms in the hotel
     * This is a helper method that simulates to extract information from a database
     *
     * @return List<Room>
     */
    public List<Room> getAllRooms() {
        return rooms.stream().collect(Collectors.toList());
    }

    public Reservation createReservation(Reservation reservation) {
        reservations.add(reservation);
        return reservation;
    }

    /**
     * Update a reservation and return updated value
     * This is a helper method that simulates to update a record in a database
     *
     * @param reservation - contains the fields to update
     * @return Reservation
     */
    public Reservation updateReservation(Reservation reservation) {
        reservations.remove(reservation);
        reservations.add(reservation);
        return reservation;
    }

    /**
     * Return a collection with all the current reservation in the hotel
     * This is a helper method that simulates to read reservations from a table in a database
     *
     * @return Set<Reservation>
     */
    public Set<Reservation> getAllReservations() {
        return reservations;
    }

}

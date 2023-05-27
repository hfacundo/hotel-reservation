package com.hotel.app.util;

import com.hotel.app.model.Reservation;
import com.hotel.app.model.Room;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

public class DataUtilTest {

    private DataUtil dataUtil;

    @BeforeEach
    public void setup() {
        dataUtil = new DataUtil();
        dataUtil.initReservations();
    }

    @Test
    public void testGetReservationNumber() {
        String reservationNumber = dataUtil.getReservationNumber();
        String reservationNumber2 = dataUtil.getReservationNumber();
        String reservationNumber3 = dataUtil.getReservationNumber();

        Assertions.assertNotNull(reservationNumber);
        Assertions.assertNotNull(reservationNumber2);
        Assertions.assertNotNull(reservationNumber3);
        Assertions.assertEquals("1001", reservationNumber);
        Assertions.assertEquals("1002", reservationNumber2);
        Assertions.assertEquals("1003", reservationNumber3);
    }

    @Test
    public void testGetReservationId() {
        int reservationId = dataUtil.getReservationId();
        int reservationId2 = dataUtil.getReservationId();
        int reservationId3 = dataUtil.getReservationId();

        Assertions.assertEquals(1, reservationId);
        Assertions.assertEquals(2, reservationId2);
        Assertions.assertEquals(3, reservationId3);
    }

    @Test
    public void testGetAllRooms() {
        List<Room> rooms = dataUtil.getAllRooms();

        Assertions.assertNotNull(rooms);
        Assertions.assertEquals(3, rooms.size());
    }

    @Test
    public void testCreateReservation() {
        Reservation reservation = new Reservation();
        reservation.setReservationNumber("123");

        Reservation createdReservation = dataUtil.createReservation(reservation);

        Assertions.assertNotNull(createdReservation);
        Assertions.assertEquals(reservation, createdReservation);
        Assertions.assertEquals("123", createdReservation.getReservationNumber());
    }

    @Test
    public void testUpdateReservation() {
        Reservation reservation = new Reservation();
        reservation.setId(1);
        reservation.setReservationNumber("123");
        reservation.setClientFullName("John Do");

        Reservation createdReservation = dataUtil.createReservation(reservation);
        Assertions.assertEquals(1, dataUtil.getAllReservations().size());

        // updating Client name of previously created reservation
        createdReservation.setClientFullName("John Doe");

        Reservation updatedReservation = dataUtil.updateReservation(createdReservation);

        Assertions.assertNotNull(updatedReservation);
        Assertions.assertEquals("123", updatedReservation.getReservationNumber());
        Assertions.assertNotEquals("John Do", updatedReservation.getClientFullName());
        Assertions.assertEquals("John Doe", updatedReservation.getClientFullName());
    }

    @Test
    public void testGetAllReservations() {
        Set<Reservation> reservations = dataUtil.getAllReservations();

        Assertions.assertNotNull(reservations);
        Assertions.assertEquals(0, reservations.size());
    }

}

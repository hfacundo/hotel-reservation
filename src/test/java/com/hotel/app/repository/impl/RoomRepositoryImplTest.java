package com.hotel.app.repository.impl;

import com.hotel.app.model.Reservation;
import com.hotel.app.model.Room;
import com.hotel.app.repository.ReservationRepository;
import com.hotel.app.util.DataUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@ExtendWith(MockitoExtension.class)
public class RoomRepositoryImplTest {

    @Mock
    private DataUtil dataUtil;

    @Mock
    private ReservationRepository reservationRepository;

    @InjectMocks
    private RoomRepositoryImpl roomRepository;

    @Test
    public void testFindRoomsAvailableByDate_Success() {
        LocalDate fromDate = LocalDate.now().plusDays(1);
        LocalDate toDate = LocalDate.now().plusDays(5);
        Room room1 = new Room(101);
        Room room2 = new Room(102);
        Room room3 = new Room(103);
        Mockito.when(dataUtil.getAllRooms()).thenReturn(List.of(room1, room2, room3));
        Mockito.when(reservationRepository.findAll()).thenReturn(Collections.EMPTY_SET);

        List<Room> availableRooms = roomRepository.findRoomsAvailableByDate(fromDate, toDate);

        Assertions.assertNotNull(availableRooms);
        Assertions.assertEquals(3, availableRooms.size());
    }

    @Test
    public void testFindRoomsAvailableByDate_Success2() {
        LocalDate fromDate = LocalDate.now().plusDays(1);
        LocalDate toDate = LocalDate.now().plusDays(5);
        List<Room> rooms = new ArrayList<>(){{
            add(new Room(101));
            add(new Room(102));
            add(new Room(103));
        }};

        Reservation reservation = new Reservation();
        reservation.setId(1);
        reservation.setReservationNumber("123");
        // Room 101 is reserved, so it will not be returned
        reservation.setRoomNumber(101);
        reservation.setFromDate(fromDate);
        reservation.setToDate(toDate);

        Mockito.when(dataUtil.getAllRooms()).thenReturn(rooms);
        Mockito.when(reservationRepository.findAll()).thenReturn(Set.of(reservation));

        List<Room> availableRooms = roomRepository.findRoomsAvailableByDate(fromDate, toDate);

        Assertions.assertNotNull(availableRooms);
        Assertions.assertEquals(2, availableRooms.size());
        for (Room room : availableRooms) {
            Assertions.assertTrue(room.getRoomNumber() != 101);
        }
    }

    @Test
    public void testFindRoomsAvailableByDate_Success3() {
        LocalDate fromDate = LocalDate.now().plusDays(2);
        LocalDate toDate = LocalDate.now().plusDays(5);
        LocalDate newFromDate = LocalDate.now().plusDays(1);
        LocalDate newToDate = LocalDate.now().plusDays(6);
        List<Room> rooms = new ArrayList<>(){{
            add(new Room(101));
            add(new Room(102));
            add(new Room(103));
        }};

        Reservation reservation = new Reservation();
        reservation.setId(1);
        reservation.setReservationNumber("123");
        // Room 101 is reserved, so it will not be returned
        reservation.setRoomNumber(101);
        reservation.setFromDate(fromDate);
        reservation.setToDate(toDate);

        Mockito.when(dataUtil.getAllRooms()).thenReturn(rooms);
        Mockito.when(reservationRepository.findAll()).thenReturn(Set.of(reservation));

        List<Room> availableRooms = roomRepository.findRoomsAvailableByDate(newFromDate, newToDate);

        Assertions.assertNotNull(availableRooms);
        Assertions.assertEquals(2, availableRooms.size());
        for (Room room : availableRooms) {
            Assertions.assertTrue(room.getRoomNumber() != 101);
        }
    }

    @Test
    public void testFindRoomsAvailableByDate_Success4() {

        Reservation reservation = new Reservation();
        reservation.setId(1);
        reservation.setReservationNumber("1001");
        reservation.setRoomNumber(101);
        reservation.setFromDate(LocalDate.now().plusDays(1));
        reservation.setToDate(LocalDate.now().plusDays(2));

        Reservation reservation2 = new Reservation();
        reservation2.setId(2);
        reservation2.setReservationNumber("1002");
        reservation2.setRoomNumber(102);
        reservation2.setFromDate(LocalDate.now().plusDays(1));
        reservation2.setToDate(LocalDate.now().plusDays(2));

        Reservation reservation3 = new Reservation();
        reservation3.setId(3);
        reservation3.setReservationNumber("1003");
        reservation3.setRoomNumber(103);
        reservation3.setFromDate(LocalDate.now().plusDays(1));
        reservation3.setToDate(LocalDate.now().plusDays(3));

        List<Room> rooms = new ArrayList<>(){{
            add(new Room(101));
            add(new Room(102));
            add(new Room(103));
        }};

        Mockito.when(dataUtil.getAllRooms()).thenReturn(rooms);
        Mockito.when(reservationRepository.findAll()).thenReturn(Set.of(reservation, reservation2, reservation3));
        List<Room> availableRooms = roomRepository.findRoomsAvailableByDate(LocalDate.now().plusDays(2), LocalDate.now().plusDays(3));

        Assertions.assertNotNull(availableRooms);
        Assertions.assertFalse(availableRooms.isEmpty());
    }

    @Test
    public void testIsRoomAvailableByDate_True() {
        LocalDate fromDate = LocalDate.now().plusDays(1);
        LocalDate toDate = LocalDate.now().plusDays(5);
        Mockito.when(reservationRepository.findAll()).thenReturn(Collections.EMPTY_SET);
        boolean isRoomAvailable = roomRepository.isRoomAvailableByDate(101, fromDate, toDate);
        Assertions.assertTrue(isRoomAvailable);
    }

    @Test
    public void testIsRoomAvailableByDate_True2() {
        LocalDate fromDate = LocalDate.now().plusDays(1);
        LocalDate toDate = LocalDate.now().plusDays(5);
        Reservation reservation = new Reservation();
        reservation.setId(1);
        reservation.setReservationNumber("123");
        reservation.setRoomNumber(101);
        reservation.setFromDate(fromDate);
        reservation.setToDate(toDate);

        Reservation reservation2 = new Reservation();
        reservation2.setId(2);
        reservation2.setReservationNumber("124");
        reservation2.setRoomNumber(102);
        reservation2.setFromDate(fromDate);
        reservation2.setToDate(toDate);

        Mockito.when(reservationRepository.findAll()).thenReturn(Set.of(reservation, reservation2));
        boolean isRoomAvailable = roomRepository.isRoomAvailableByDate(103, fromDate, toDate);
        Assertions.assertTrue(isRoomAvailable);
    }

    @Test
    public void testIsRoomAvailableByDate_True3() {
        LocalDate fromDate = LocalDate.now().plusDays(1);
        LocalDate toDate = LocalDate.now().plusDays(3);
        LocalDate newFromDate = LocalDate.now().plusDays(4);
        LocalDate newToDate = LocalDate.now().plusDays(5);
        Reservation reservation = new Reservation();
        reservation.setId(1);
        reservation.setReservationNumber("123");
        reservation.setRoomNumber(101);
        reservation.setFromDate(fromDate);
        reservation.setToDate(toDate);

        Reservation reservation2 = new Reservation();
        reservation2.setId(2);
        reservation2.setReservationNumber("124");
        reservation2.setRoomNumber(102);
        reservation2.setFromDate(fromDate);
        reservation2.setToDate(toDate);

        Mockito.when(reservationRepository.findAll()).thenReturn(Set.of(reservation, reservation2));
        boolean isRoomAvailable = roomRepository.isRoomAvailableByDate(102, newFromDate, newToDate);
        Assertions.assertTrue(isRoomAvailable);
    }

    @Test
    public void testIsRoomAvailableByDate_False() {
        LocalDate fromDate = LocalDate.now().plusDays(1);
        LocalDate toDate = LocalDate.now().plusDays(5);
        Reservation reservation = new Reservation();
        reservation.setId(1);
        reservation.setReservationNumber("123");
        reservation.setRoomNumber(101);
        reservation.setFromDate(fromDate);
        reservation.setToDate(toDate);

        Reservation reservation2 = new Reservation();
        reservation2.setId(2);
        reservation2.setReservationNumber("124");
        reservation2.setRoomNumber(102);
        reservation2.setFromDate(fromDate);
        reservation2.setToDate(toDate);

        Mockito.when(reservationRepository.findAll()).thenReturn(Set.of(reservation, reservation2));
        boolean isRoomAvailable = roomRepository.isRoomAvailableByDate(102, fromDate, toDate);
        Assertions.assertFalse(isRoomAvailable);
    }

    @Test
    public void testIsRoomAvailableByDate_False2() {
        LocalDate fromDate = LocalDate.now().plusDays(2);
        LocalDate toDate = LocalDate.now().plusDays(5);
        LocalDate newFromDate = LocalDate.now().plusDays(1);
        LocalDate newToDate = LocalDate.now().plusDays(6);
        Reservation reservation = new Reservation();
        reservation.setId(1);
        reservation.setReservationNumber("123");
        reservation.setRoomNumber(101);
        reservation.setFromDate(fromDate);
        reservation.setToDate(toDate);

        Reservation reservation2 = new Reservation();
        reservation2.setId(2);
        reservation2.setReservationNumber("124");
        reservation2.setRoomNumber(102);
        reservation2.setFromDate(fromDate);
        reservation2.setToDate(toDate);

        Mockito.when(reservationRepository.findAll()).thenReturn(Set.of(reservation, reservation2));
        boolean isRoomAvailable = roomRepository.isRoomAvailableByDate(102, newFromDate, newToDate);
        Assertions.assertFalse(isRoomAvailable);
    }

    @Test
    public void testIsValidDateRange() {
        // These dates represent a date range from a previous reservation
        LocalDate fromDate = LocalDate.now();
        LocalDate toDate = LocalDate.now().plusDays(2);

        // We are trying to make a reservation between these dates
        // no overlap
        LocalDate newFromDate = LocalDate.now().minusDays(10);
        LocalDate newToDate = LocalDate.now().minusDays(8);
        boolean isValid = roomRepository.isValidDateRange(fromDate, toDate, newFromDate, newToDate);
        Assertions.assertTrue(isValid);

        // overlaps
        newFromDate = LocalDate.now().minusDays(10);
        newToDate = LocalDate.now().plusDays(1);
        isValid = roomRepository.isValidDateRange(fromDate, toDate, newFromDate, newToDate);
        Assertions.assertFalse(isValid);

        // overlaps
        newFromDate = LocalDate.now();
        newToDate = LocalDate.now().plusDays(2);
        isValid = roomRepository.isValidDateRange(fromDate, toDate, newFromDate, newToDate);
        Assertions.assertFalse(isValid);

        // overlaps
        newFromDate = LocalDate.now().plusDays(1);
        newToDate = LocalDate.now().plusDays(3);
        isValid = roomRepository.isValidDateRange(fromDate, toDate, newFromDate, newToDate);
        Assertions.assertFalse(isValid);

        // no overlap
        newFromDate = LocalDate.now().plusDays(2);
        newToDate = LocalDate.now().plusDays(3);
        isValid = roomRepository.isValidDateRange(fromDate, toDate, newFromDate, newToDate);
        Assertions.assertTrue(isValid);

        // no overlaps
        newFromDate = LocalDate.now().plusDays(3);
        newToDate = LocalDate.now().plusDays(4);
        isValid = roomRepository.isValidDateRange(fromDate, toDate, newFromDate, newToDate);
        Assertions.assertTrue(isValid);
    }

}

package com.hotel.app.service.impl;

import com.hotel.app.exceptions.ReservationException;
import com.hotel.app.model.Reservation;
import com.hotel.app.model.Room;
import com.hotel.app.repository.ReservationRepository;
import com.hotel.app.repository.RoomRepository;
import com.hotel.app.util.DataUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ReservationServiceImplTest {

    @Mock
    private ReservationRepository reservationRepository;

    @Mock
    private RoomRepository roomRepository;

    @Mock
    private DataUtil dataUtil;

    @InjectMocks
    private ReservationServiceImpl reservationService;

    @Test
    public void testInit() {
        doNothing().when(dataUtil).initReservations();

        reservationService.init();

        Mockito.verify(dataUtil, times(1)).initReservations();
    }

    @Test
    public void testCreateReservation_Success() throws ReservationException {
        LocalDate fromDate = LocalDate.now().plusDays(1);
        LocalDate toDate = fromDate.plusDays(5);
        Room room = new Room();
        room.setRoomNumber(101);
        Reservation reservation = new Reservation();
        reservation.setClientFullName("John Doe");
        reservation.setFromDate(fromDate);
        reservation.setToDate(toDate);
        reservation.setRoomNumber(101);

        when(roomRepository.findRoomsAvailableByDate(fromDate, toDate)).thenReturn(List.of(room));
        when(dataUtil.getReservationNumber()).thenReturn("123");
        when(reservationRepository.create(reservation)).thenReturn(reservation);

        Reservation result = reservationService.createReservation(reservation);

        Assertions.assertNotNull(result);
        Assertions.assertEquals("123", result.getReservationNumber());
        Assertions.assertEquals(101, result.getRoomNumber());

        Mockito.verify(reservationRepository, times(1)).create(reservation);
    }

    @Test
    public void testCreateReservation_NoRoomsAvailable() {
        LocalDate fromDate = LocalDate.now().plusDays(1);
        LocalDate toDate = fromDate.plusDays(5);
        Reservation reservation = new Reservation();
        reservation.setId(1);
        reservation.setReservationNumber("123");
        reservation.setFromDate(fromDate);
        reservation.setToDate(toDate);

        when(roomRepository.findRoomsAvailableByDate(fromDate, toDate)).thenReturn(Collections.EMPTY_LIST);
        Assertions.assertThrows(ReservationException.class, () -> reservationService.createReservation(reservation));
    }

    @Test
    public void testCreateReservation_WrongDates() {
        LocalDate fromDate = LocalDate.now().plusDays(5);
        LocalDate toDate = LocalDate.now().plusDays(1);
        Reservation reservation = new Reservation();
        reservation.setId(1);
        reservation.setReservationNumber("123");
        reservation.setFromDate(fromDate);
        reservation.setToDate(toDate);

        Room room = new Room();
        room.setRoomNumber(101);

        when(roomRepository.findRoomsAvailableByDate(fromDate, toDate)).thenReturn(List.of(room));

        Assertions.assertThrows(ReservationException.class, () -> reservationService.createReservation(reservation));
        Mockito.verify(reservationRepository, never()).create(any(Reservation.class));
    }

    @Test
    public void testUpdateReservation_Success() throws ReservationException {
        LocalDate fromDate = LocalDate.now().plusDays(1);
        LocalDate toDate = LocalDate.now().plusDays(5);
        Reservation reservation = new Reservation();
        reservation.setId(1);
        reservation.setReservationNumber("123");
        reservation.setFromDate(fromDate);
        reservation.setToDate(toDate);
        Optional<Reservation> optional = Optional.of(reservation);

        when(reservationRepository.findById(1)).thenReturn(optional);
        when(reservationRepository.update(reservation)).thenReturn(reservation);

        Reservation updatedReservation = reservationService.updateReservation(1, reservation);

        Assertions.assertNotNull(updatedReservation);
        Mockito.verify(reservationRepository, times(1)).update(reservation);
    }

    @Test
    public void testUpdateReservation_ReservationNotFound() {
        LocalDate fromDate = LocalDate.now().plusDays(1);
        LocalDate toDate = LocalDate.now().plusDays(5);
        Reservation reservation = new Reservation();
        reservation.setId(1);
        reservation.setReservationNumber("123");
        reservation.setFromDate(fromDate);
        reservation.setToDate(toDate);
        when(reservationRepository.findById(1)).thenReturn(Optional.empty());
        Assertions.assertThrows(ReservationException.class, () -> reservationService.updateReservation(1, reservation));
    }

    @Test
    public void testUpdateReservation_WrongDates() {
        LocalDate fromDate = LocalDate.now().plusDays(5);
        LocalDate toDate = LocalDate.now().plusDays(1);
        Reservation reservation = new Reservation();
        reservation.setId(1);
        reservation.setClientFullName("John Doe");
        reservation.setReservationNumber("123");
        reservation.setFromDate(fromDate);
        reservation.setToDate(toDate);
        when(reservationRepository.findById(1)).thenReturn(Optional.of(reservation));
        Assertions.assertThrows(ReservationException.class, () -> reservationService.updateReservation(1, reservation));
    }

    @Test
    public void testUpdateReservation_RoomUnavailable() {
        LocalDate fromDate = LocalDate.now().plusDays(1);
        LocalDate toDate = LocalDate.now().plusDays(5);

        Reservation originalReservation = new Reservation();
        originalReservation.setId(1);
        originalReservation.setReservationNumber("123");
        originalReservation.setRoomNumber(1);
        originalReservation.setFromDate(fromDate);
        originalReservation.setToDate(toDate);

        Reservation reservation = new Reservation();
        reservation.setId(1);
        reservation.setReservationNumber("123");
        reservation.setRoomNumber(2);
        reservation.setFromDate(fromDate);
        reservation.setToDate(toDate);

        when(reservationRepository.findById(1)).thenReturn(Optional.of(originalReservation));
        when(roomRepository.isRoomAvailableByDate(reservation.getRoomNumber(), fromDate, toDate)).thenReturn(false);

        Assertions.assertThrows(ReservationException.class, () -> reservationService.updateReservation(1, reservation));
    }

    @Test
    public void testGetAllReservations() {
        Reservation reservation = new Reservation();
        reservation.setId(1);
        reservation.setReservationNumber("123");
        when(reservationRepository.findAll()).thenReturn(Set.of(reservation));

        Set<Reservation> reservations = reservationService.getAllReservations();

        Assertions.assertNotNull(reservation);
        Assertions.assertEquals(1, reservations.size());
        Mockito.verify(reservationRepository, times(1)).findAll();
    }

}

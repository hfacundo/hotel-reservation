package com.hotel.app.repository.impl;

import com.hotel.app.model.Reservation;
import com.hotel.app.util.DataUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.Set;

@ExtendWith(MockitoExtension.class)
public class ReservationRepositoryImplTest {

    @Mock
    private DataUtil dataUtil;

    @InjectMocks
    private ReservationRepositoryImpl reservationRepository;

    @Test
    public void testCreate() {
        Reservation reservation = new Reservation();
        reservation.setReservationNumber("123");
        reservation.setClientFullName("John Doe");
        Mockito.when(dataUtil.getReservationId()).thenReturn(1);
        Mockito.when(dataUtil.createReservation(reservation)).thenReturn(reservation);

        Assertions.assertEquals(0, reservation.getId());
        Reservation createdReservation = reservationRepository.create(reservation);
        Assertions.assertNotNull(createdReservation);
        Assertions.assertEquals(1, reservation.getId());
    }

    @Test
    public void testUpdate() {
        Reservation reservation = new Reservation();
        reservation.setId(1);
        reservation.setReservationNumber("123");
        reservation.setClientFullName("John Do");

        Reservation updatedReservation = new Reservation();
        reservation.setId(1);
        reservation.setReservationNumber("123");
        reservation.setClientFullName("John Doe");
        Mockito.when(dataUtil.updateReservation(reservation)).thenReturn(updatedReservation);

        Reservation response = reservationRepository.update(reservation);

        Assertions.assertEquals(updatedReservation.getClientFullName(), response.getClientFullName());
    }

    @Test
    public void testFindByReservationNumber_Found() {
        Reservation reservation = new Reservation();
        reservation.setId(1);
        reservation.setReservationNumber("123");
        reservation.setClientFullName("John Doe");

        Mockito.when(dataUtil.getAllReservations()).thenReturn(Set.of(reservation));

        Optional<Reservation> optional = reservationRepository.findById(1);

        Assertions.assertTrue(optional.isPresent());
    }

    @Test
    public void testFindByReservationNumber_NotFound() {
        Reservation reservation = new Reservation();
        reservation.setId(1);
        reservation.setReservationNumber("123");
        reservation.setClientFullName("John Doe");

        Mockito.when(dataUtil.getAllReservations()).thenReturn(Set.of(reservation));

        Optional<Reservation> optional = reservationRepository.findById(2);

        Assertions.assertFalse(optional.isPresent());
    }

    @Test
    public void testFindAll() {
        Reservation reservation = new Reservation();
        reservation.setId(1);
        reservation.setReservationNumber("123");
        reservation.setClientFullName("John Doe");
        Mockito.when(dataUtil.getAllReservations()).thenReturn(Set.of(reservation));

        Set<Reservation> reservations = reservationRepository.findAll();

        Assertions.assertNotNull(reservations);
        Assertions.assertEquals(1, reservations.size());
    }
}

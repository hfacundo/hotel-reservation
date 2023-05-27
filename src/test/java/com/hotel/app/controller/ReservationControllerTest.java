package com.hotel.app.controller;

import com.hotel.app.exceptions.ReservationException;
import com.hotel.app.model.Reservation;
import com.hotel.app.response.BasicResponse;
import com.hotel.app.service.ReservationService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashSet;
import java.util.Set;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

public class ReservationControllerTest {

    @Mock
    private ReservationService reservationService;

    @InjectMocks
    private ReservationController reservationController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCreateReservation_Success() throws ReservationException {
        Reservation reservation = new Reservation();
        reservation.setId(1);
        reservation.setReservationNumber("123");
        BasicResponse<Reservation> expectedResponse = new BasicResponse<>();
        expectedResponse.setData(reservation);
        expectedResponse.setCode(HttpStatus.CREATED.value());
        expectedResponse.setMessage(HttpStatus.CREATED.getReasonPhrase());

        when(reservationService.createReservation(reservation)).thenReturn(reservation);

        ResponseEntity<BasicResponse<Reservation>> responseEntity = reservationController.createReservation(reservation);

        Assertions.assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        Assertions.assertEquals(201, responseEntity.getBody().getCode());
        Assertions.assertEquals("Created", responseEntity.getBody().getMessage());
        Assertions.assertEquals(reservation, responseEntity.getBody().getData());
        Mockito.verify(reservationService, times(1)).createReservation(reservation);
    }

    @Test
    public void testCreateReservation_Failure() throws ReservationException {
        Reservation reservation = new Reservation();
        BasicResponse<Reservation> expectedResponse = new BasicResponse<>();
        expectedResponse.setCode(HttpStatus.BAD_REQUEST.value());
        expectedResponse.setMessage("Invalid reservation");

        when(reservationService.createReservation(reservation)).thenThrow(new ReservationException("Invalid reservation"));

        ResponseEntity<BasicResponse<Reservation>> responseEntity = reservationController.createReservation(reservation);

        Assertions.assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        Assertions.assertEquals(400, responseEntity.getBody().getCode());
        Assertions.assertEquals("Invalid reservation", responseEntity.getBody().getMessage());
        Assertions.assertNull(responseEntity.getBody().getData());
        Mockito.verify(reservationService, times(1)).createReservation(reservation);
    }

    @Test
    public void testUpdateReservation_Success() throws ReservationException {
        String reservationNumber = "123";
        Reservation reservation = new Reservation();
        reservation.setId(1);
        reservation.setReservationNumber(reservationNumber);
        BasicResponse<Reservation> expectedResponse = new BasicResponse<>();
        expectedResponse.setData(reservation);
        expectedResponse.setCode(HttpStatus.OK.value());
        expectedResponse.setMessage(HttpStatus.OK.getReasonPhrase());

        when(reservationService.updateReservation(1, reservation)).thenReturn(reservation);

        ResponseEntity<BasicResponse<Reservation>> responseEntity = reservationController.updateReservation(1, reservation);

        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assertions.assertEquals(200, responseEntity.getBody().getCode());
        Assertions.assertEquals("OK", responseEntity.getBody().getMessage());
        Assertions.assertEquals(reservation, responseEntity.getBody().getData());
        Mockito.verify(reservationService, times(1)).updateReservation(1, reservation);
    }

    @Test
    public void testUpdateReservation_Failure() throws ReservationException {
        Reservation reservation = new Reservation();
        reservation.setId(1);
        BasicResponse<Reservation> expectedResponse = new BasicResponse<>();
        expectedResponse.setCode(HttpStatus.BAD_REQUEST.value());
        expectedResponse.setMessage("Invalid reservation");

        when(reservationService.updateReservation(1, reservation)).thenThrow(new ReservationException("Invalid reservation"));

        ResponseEntity<BasicResponse<Reservation>> responseEntity = reservationController.updateReservation(1, reservation);

        Assertions.assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        Assertions.assertEquals(400, responseEntity.getBody().getCode());
        Assertions.assertEquals("Invalid reservation", responseEntity.getBody().getMessage());
        Assertions.assertNull(responseEntity.getBody().getData());
        Mockito.verify(reservationService, times(1)).updateReservation(1, reservation);
    }

    @Test
    public void testGetReservations() {
        Set<Reservation> reservations = new HashSet<>();
        reservations.add(new Reservation());
        BasicResponse<Set<Reservation>> expectedResponse = new BasicResponse<>();
        expectedResponse.setData(reservations);
        expectedResponse.setCode(HttpStatus.OK.value());
        expectedResponse.setMessage(HttpStatus.OK.getReasonPhrase());

        when(reservationService.getAllReservations()).thenReturn(reservations);

        // Call the method under test
        ResponseEntity<BasicResponse<Set<Reservation>>> responseEntity = reservationController.getReservations();

        // Verify the result
        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assertions.assertEquals(200, responseEntity.getBody().getCode());
        Assertions.assertEquals("OK", responseEntity.getBody().getMessage());
        Assertions.assertNotNull(responseEntity.getBody().getData());
        Mockito.verify(reservationService, times(1)).getAllReservations();
    }

}

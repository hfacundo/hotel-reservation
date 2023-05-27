package com.hotel.app.controller;

import com.hotel.app.exceptions.ReservationException;
import com.hotel.app.model.Reservation;
import com.hotel.app.response.BasicResponse;
import com.hotel.app.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Set;

/**
 * This class contains methods to process http requests for Hotel reservation
 */
@RestController
@RequestMapping("/api")
@Validated
public class ReservationController {

    @Autowired
    private ReservationService service;

    /**
     * Process POST http requests to create a reservation
     *
     * @param reservation
     *      mandatory fields:
     *          fromDate
     *          toDate
     *          clientFullName
     * @return ResponseEntity<BasicResponse<Reservation>>
     */
    @PostMapping("/create-reservation")
    public ResponseEntity<BasicResponse<Reservation>> createReservation(@Valid @RequestBody Reservation reservation) {
        BasicResponse<Reservation> response = new BasicResponse<>();
        try {
            response.setData(service.createReservation(reservation));
            response.setCode(HttpStatus.CREATED.value());
            response.setMessage(HttpStatus.CREATED.getReasonPhrase());
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (ReservationException e) {
            response.setCode(HttpStatus.BAD_REQUEST.value());
            response.setMessage(e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    /**
     * Process PUT http requests to update a reservation
     *
     * @param reservationId - id of the reservation to update, it must match with the id provided in reservation param
     * @param reservation - contains the new attributes to update
     * @return ResponseEntity<BasicResponse<Reservation>>
     */
    @PutMapping("/update-reservation/{reservationId}")
    public ResponseEntity<BasicResponse<Reservation>> updateReservation(@PathVariable Integer reservationId, @Valid @RequestBody Reservation reservation) {
        BasicResponse<Reservation> response = new BasicResponse<>();
        try {
            response.setData(service.updateReservation(reservationId, reservation));
            response.setCode(HttpStatus.OK.value());
            response.setMessage(HttpStatus.OK.getReasonPhrase());
            return ResponseEntity.ok(response);
        } catch (ReservationException e) {
            response.setCode(HttpStatus.BAD_REQUEST.value());
            response.setMessage(e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    /**
     * Process GET http requests and return all reservations
     *
     * @return ResponseEntity<BasicResponse<Reservation>>
     */
    @GetMapping("/get-reservations")
    public ResponseEntity<BasicResponse<Set<Reservation>>> getReservations() {
        BasicResponse<Set<Reservation>> response = new BasicResponse<>();
        response.setData(service.getAllReservations());
        response.setCode(HttpStatus.OK.value());
        response.setMessage(HttpStatus.OK.getReasonPhrase());
        return ResponseEntity.ok(response);
    }

}

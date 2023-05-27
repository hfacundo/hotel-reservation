package com.hotel.app.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Objects;

import static com.hotel.app.util.ErrorConstants.THIS_FIELD_CANNOT_BE_NULL;

@NoArgsConstructor
@Getter
@Setter
public class Reservation implements Comparable<Reservation>{
    private int id;
    private String reservationNumber;
    @NotNull(message = THIS_FIELD_CANNOT_BE_NULL)
    private String clientFullName;
    @NotNull(message = THIS_FIELD_CANNOT_BE_NULL)
    private LocalDate fromDate;
    @NotNull(message = THIS_FIELD_CANNOT_BE_NULL)
    private LocalDate toDate;
    private int roomNumber;

    @Override
    public int compareTo(Reservation o) {
        return this.getReservationNumber().compareTo(o.getReservationNumber());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reservation that = (Reservation) o;
        return id == that.id && Objects.equals(reservationNumber, that.reservationNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, reservationNumber);
    }
}

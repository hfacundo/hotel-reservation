package com.hotel.app.repository;

import com.hotel.app.model.Room;

import java.time.LocalDate;
import java.util.List;

public interface RoomRepository {

    List<Room> findRoomsAvailableByDate(LocalDate from, LocalDate to);

    Boolean isRoomAvailableByDate(int roomNumber, LocalDate from, LocalDate to);
}

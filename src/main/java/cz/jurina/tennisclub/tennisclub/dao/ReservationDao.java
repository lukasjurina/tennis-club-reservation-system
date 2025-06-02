package cz.jurina.tennisclub.tennisclub.dao;

import cz.jurina.tennisclub.tennisclub.entity.Reservation;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Data Access Object interface for managing Reservation entities.
 *
 * Extends the BaseDao
 *
 * @author Lukas Jurina
 */
public interface ReservationDao extends BaseDao<Reservation>{

    /**
     * Retrieves list of future reservations based on the given phone number.
     *
     * @param phoneNumber the phone number associated with the reservation
     * @return the list of reservations, empty list if there are no reservation meeting the criteria
     */
    List<Reservation> findFutureByPhoneNumber(String phoneNumber);

    /**
     * Retrieves list of reservations based on the given court ID.
     *
     * @param courtId the court ID associated with the reservation
     * @return the list of reservations, empty list if there are no reservation meeting the criteria
     */
    List<Reservation> findAllByCourtId(Long courtId);

    /**
     * Determines if new reservation can be created at the specified time slot at the given court.
     *
     * @param courtId the court ID where reservation will be created
     * @param start the start time of the reservation
     * @param end the end time of the reservation
     * @return true if there is already reservation between the two times, false otherwise
     */
    boolean existsOverlappingReservation(Long courtId, LocalDateTime start, LocalDateTime end);
}

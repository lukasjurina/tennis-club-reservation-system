package cz.jurina.tennisclub.tennisclub.service;

import cz.jurina.tennisclub.tennisclub.dto.CreateReservationDto;
import cz.jurina.tennisclub.tennisclub.dto.ReservationDto;

import java.util.List;

/**
 * Service interface for managing court reservations.
 * <p>
 * Provides functionality to create, retrieve, update, and cancel reservations,
 * includes filtering by court or customer phone number.
 * </p>
 *
 * @author Lukas Jurina
 */
public interface ReservationService {

    /**
     * Creates a new reservation.
     *
     * @param dto reservation details
     * @return calculated price of the reservation
     * @throws IllegalArgumentException if input is invalid or time overlaps with another reservation
     */
    double createReservation(CreateReservationDto dto);

    /**
     * Retrieves all reservations.
     *
     * @return list of all reservations, empty list if there are none
     */
    List<ReservationDto> getAllReservations();

    /**
     * Retrieves all reservations for a specific court sorted by date created.
     *
     * @param courtId the ID of the court
     * @return list of reservations for the given court, empty list if there are none on the given court
     */
    List<ReservationDto> getReservationsByCourtId(Long courtId);


    /**
     * Retrieves reservations based on customer phone number.
     *
     * @param phoneNumber phone number of the customer
     * @param onlyFuture if true, only future reservations will be returned
     * @return list of matching reservations, empty list if there are no reservation meeting the criteria
     * @throws IllegalArgumentException if phone number format is invalid
     */
    List<ReservationDto> getReservationsByPhoneNumber(String phoneNumber, boolean onlyFuture);

    /**
     * Updates an existing reservation.
     *
     * @param id reservation ID
     * @param dto new reservation details
     * @return new calculated price
     * @throws IllegalArgumentException if reservation ID is invalid or overlaps
     */
    double updateReservation(Long id, CreateReservationDto dto);

    /**
     * Cancels a reservation by ID.
     *
     * @param reservationId ID of the reservation to cancel
     * @throws IllegalArgumentException if the reservation ID is invalid
     */
    void cancelReservation(Long reservationId);
}
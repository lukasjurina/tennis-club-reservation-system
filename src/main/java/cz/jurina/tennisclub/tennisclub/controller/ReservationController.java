package cz.jurina.tennisclub.tennisclub.controller;

import cz.jurina.tennisclub.tennisclub.dto.CreateReservationDto;
import cz.jurina.tennisclub.tennisclub.dto.ReservationDto;
import cz.jurina.tennisclub.tennisclub.service.ReservationService;
import jakarta.annotation.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * REST controller providing endpoints for managing reservations.
 * Supports creating, updating, retrieving, and deleting reservations.
 *
 * Path: <code>/api/reservations</code>
 *
 * @author Lukas Jurina
 */
@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

    @Resource
    private ReservationService reservationService;

    /**
     * Retrieves all reservations.
     *
     * @return list of all reservations
     */
    @GetMapping
    public ResponseEntity<List<ReservationDto>> getAllReservations(){
        return ResponseEntity.ok(reservationService.getAllReservations());
    }

    /**
     * Retrieves all reservations for a specific court by its ID.
     *
     * @param id the ID of the court
     * @return list of reservations for the specified court
     */
    @GetMapping("/by-court/{id}")
    public ResponseEntity<List<ReservationDto>> getReservationsByCourt(@PathVariable Long id){
        return ResponseEntity.ok(reservationService.getReservationsByCourtId(id));
    }

    /**
     * Retrieves reservations by customer's phone number.
     *
     * @param phoneNumber the customer's phone number
     * @param futureOnly if true, only future reservations will be returned
     * @return list of matching reservations
     */
    @GetMapping("/by-phone/{phoneNumber}")
    public ResponseEntity<List<ReservationDto>> getReservationsByPhone(
            @PathVariable String phoneNumber,
            @RequestParam(name = "futureOnly", required = false, defaultValue = "false") boolean futureOnly) {

        return ResponseEntity.ok(reservationService.getReservationsByPhoneNumber(phoneNumber, futureOnly));
    }

    /**
     * Updates an existing reservation.
     *
     * @param id the ID of the reservation to update
     * @param dto the updated reservation data
     * @return the new calculated price of the reservation
     */
    @PutMapping("/{id}")
    public ResponseEntity<Double> updateReservation(@PathVariable Long id, @RequestBody CreateReservationDto dto) {
        return ResponseEntity.ok(reservationService.updateReservation(id, dto));
    }

    /**
     * Deletes a reservation by ID.
     *
     * @param id the ID of the reservation to delete
     * @return HTTP status
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
        reservationService.cancelReservation(id);
        return ResponseEntity.ok(null);
    }

    /**
     * Creates a new reservation.
     *
     * @param dto the reservation request data
     * @return the calculated price of the created reservation
     */
    @PostMapping("/new-reservation")
    public ResponseEntity<Double> createReservation(@RequestBody CreateReservationDto dto) {
        return ResponseEntity.ok(reservationService.createReservation(dto));
    }

}

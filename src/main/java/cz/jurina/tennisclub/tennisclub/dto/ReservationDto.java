package cz.jurina.tennisclub.tennisclub.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Data Transfer Object representing a reservation.
 *
 * Contains all relevant information about a reservation, including:
 * - IDs for reservation and court
 * - Customer details
 * - Reservation time window
 * - Type of game (single or double)
 * - Price of the reservation
 * - Timestamp when the reservation was created
 *
 * Date-time fields use the format "dd.MM.yyyy HH:mm".
 *
 * @author Lukas Jurina
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReservationDto {
    private Long id;
    private Long courtId;
    private String customerName;
    private String customerSurname;
    private String customerPhoneNumber;
    @JsonFormat(pattern = "dd.MM.yyyy HH:mm")
    private LocalDateTime startTime;
    @JsonFormat(pattern = "dd.MM.yyyy HH:mm")
    private LocalDateTime endTime;
    private Boolean isDouble;
    private Double price;
    @JsonFormat(pattern = "dd.MM.yyyy HH:mm")
    private LocalDateTime timeCreated;
}

package cz.jurina.tennisclub.tennisclub.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * Data Transfer Object used for creating a new reservation.
 *
 * Includes data such as customer information, court ID,
 * reservation time window, and game type (single or double).
 * The customer is created automatically if they do not already exist.
 *
 * Date-time fields follow the format "dd.MM.yyyy HH:mm".
 *
 * @author Lukas Jurina
 */
@Data
@AllArgsConstructor
public class CreateReservationDto {
    private Long courtId;
    private String customerName;
    private String customerSurname;
    private String customerPhoneNumber;
    @JsonFormat(pattern = "dd.MM.yyyy HH:mm")
    private LocalDateTime startTime;
    @JsonFormat(pattern = "dd.MM.yyyy HH:mm")
    private LocalDateTime endTime;
    private Boolean isDouble;
}

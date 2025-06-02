package cz.jurina.tennisclub.tennisclub.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object representing a tennis court.
 *
 * Contains the court ID, surface type, and minute price for reservation;
 *
 * @author Lukas Jurina
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourtDto {
    private Long id;
    private String surfaceType;
    private Double price;
}

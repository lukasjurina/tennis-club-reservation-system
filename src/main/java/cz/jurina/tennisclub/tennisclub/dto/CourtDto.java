package cz.jurina.tennisclub.tennisclub.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object representing a tennis court with surface details.
 * Used for transferring court data to the client layer.
 *
 * Contains the court ID, surface type, and price for reservation calculation or display.
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

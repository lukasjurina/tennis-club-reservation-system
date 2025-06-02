package cz.jurina.tennisclub.tennisclub.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object used for creating a new tennis court.
 *
 * Contains only the surface type, which links the court to an existing court surface.
 *
 * @author Lukas Jurina
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateCourtDto {
    private String surfaceType;
}

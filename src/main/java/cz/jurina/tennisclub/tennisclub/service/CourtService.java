package cz.jurina.tennisclub.tennisclub.service;

import cz.jurina.tennisclub.tennisclub.dto.CourtDto;
import cz.jurina.tennisclub.tennisclub.dto.CreateCourtDto;

import java.util.List;

/**
 * Service interface for managing tennis courts.
 * Provides operations for creating, retrieving, updating, and deleting courts.
 *
 * @author Lukas Jurina
 */
public interface CourtService {

    /**
     * Creates a new tennis court using the specified data.
     *
     * @param dto data required to create a court
     * @throws IllegalArgumentException if the specified court surface does not exist
     */
    void createCourt(CreateCourtDto dto);

    /**
     * Retrieves all courts in the database.
     *
     * @return list of all courts
     */
    List<CourtDto> getAllCourts();

    /**
     * Retrieves a specific court by its ID.
     *
     * @param id the ID of the court
     * @return {@link CourtDto}
     * @throws IllegalArgumentException if there is no court with the given ID
     */
    CourtDto getCourtById(Long id);

    /**
     * Deletes the court with the given ID.
     *
     * @param id the ID of the court to delete
     * @throws IllegalArgumentException if there is no court with the given ID
     */
    void deleteCourt(Long id);

    /**
     * Updates the court with the specified ID using the given data.
     *
     * @param id the ID of the court to update
     * @param dto new data for the court
     * @throws IllegalArgumentException if no court exists with the given ID or the specified surface type does not exist
     */
    void updateCourt(Long id, CreateCourtDto dto);
}

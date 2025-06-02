package cz.jurina.tennisclub.tennisclub.controller;

import cz.jurina.tennisclub.tennisclub.dto.CourtDto;
import cz.jurina.tennisclub.tennisclub.dto.CreateCourtDto;
import cz.jurina.tennisclub.tennisclub.service.CourtService;
import jakarta.annotation.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * REST controller providing endpoints for managing tennis courts.
 * Handles creation, retrieval, updating, and deletion of court records.
 *
 * Path: <code>/api/courts</code>
 *
 * @author Lukas Jurina
 */
@RestController
@RequestMapping("/api/courts")
public class CourtController {

    @Resource
    private CourtService courtService;

    /**
     * Creates a new tennis court.
     *
     * @param dto DTO containing surface type for the new court
     * @return HTTP 200 OK if court is created successfully
     */
    @PostMapping
    public ResponseEntity<Void> createCourt(@RequestBody CreateCourtDto dto) {
        courtService.createCourt(dto);
        return ResponseEntity.ok(null);
    }

    /**
     * Retrieves all courts.
     *
     * @return list of all courts
     */
    @GetMapping
    public ResponseEntity<List<CourtDto>> getAllCourts() {
        return ResponseEntity.ok(courtService.getAllCourts());
    }

    /**
     * Retrieves a court by its ID.
     *
     * @param id the ID of the court
     * @return the court data
     */
    @GetMapping("/{id}")
    public ResponseEntity<CourtDto> getCourtById(@PathVariable Long id) {
        return ResponseEntity.ok(courtService.getCourtById(id));
    }

    /**
     * Deletes a court by its ID.
     *
     * @param id the ID of the court to delete
     * @return HTTP 200 OK if deletion is successful
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCourt(@PathVariable Long id) {
        courtService.deleteCourt(id);
        return ResponseEntity.ok(null);
    }

    /**
     * Updates the surface of an existing court.
     *
     * @param id the ID of the court to update
     * @param dto DTO containing the new surface type
     * @return HTTP 200 OK if update is successful
     */
    @PutMapping("/{id}")
    public ResponseEntity<Void> updateCourtSurface(@PathVariable Long id, @RequestBody CreateCourtDto dto) {
        courtService.updateCourt(id, dto);
        return ResponseEntity.ok(null);
    }
}

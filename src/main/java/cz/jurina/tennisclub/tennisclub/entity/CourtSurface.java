package cz.jurina.tennisclub.tennisclub.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import lombok.Data;

/**
 * Entity representing a surface type used on tennis courts.
 *
 * Each surface type has a name and price, and supports soft deletion.
 * Courts are linked to a surface with this entity.
 *
 * Mapped to the "court_surfaces" table in the database.
 *
 * @author Lukas Jurina
 */
@Entity
@Table(name = "court_surfaces")
@Data
public class CourtSurface {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "court_surface_id")
    private Long id;

    @Column(name = "surface_type", nullable = false, unique = true)
    private String surfaceType;

    @Column(name = "price", nullable = false)
    private Double price;

    @Column(name = "deleted")
    private boolean deleted = false;
}
